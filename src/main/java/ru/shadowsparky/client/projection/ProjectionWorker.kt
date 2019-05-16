/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.projection

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.shadowsparky.client.mvvm.views.VideoView
import ru.shadowsparky.client.ConnectionType
import ru.shadowsparky.client.exceptions.CorruptedDataException
import ru.shadowsparky.client.exceptions.IncorrectPasswordException
import ru.shadowsparky.client.interfaces.Resultable
import ru.shadowsparky.client.objects.Constants.LOCALHOST
import ru.shadowsparky.client.objects.Constants.PORT
import ru.shadowsparky.client.objects.Injection
import ru.shadowsparky.screencast.proto.HandledPictureOuterClass
import ru.shadowsparky.screencast.proto.PreparingDataOuterClass
import java.io.Closeable
import java.net.Socket

open class ProjectionWorker(
        private val handler: Resultable,
        val addr: String,
        private val port: Int = PORT
) : Closeable {
    private var video: VideoView? = null
    private var socket: Socket? = null
    private val log = Injection.provideLogger()
    private lateinit var pData: PreparingDataOuterClass.PreparingData
    private var saved_data = Injection.provideQueue()
    private var decoder: Decoder? = null
    private val type: ConnectionType = if (addr == LOCALHOST) ConnectionType.adb else ConnectionType.wifi
    var handling: Boolean = false
        set(value) {
            if (value) {
                saved_data.clear()
                log.printInfo("Handling enabled")
            } else {
                close()
                log.printInfo("Handling disabled and disposed")
            }
            field = value
        }

    fun showProjection() {
        video = VideoView(this, "Проецирование", type)
        decode()
    }
    open fun start() = connectToServer()
    fun stop() = socket?.close()

    override fun close() {
        socket?.close()
        saved_data.clear()
        decoder = null
        video?.dispose()
        System.gc()
        System.runFinalization()
    }

    open fun connectToServer() = GlobalScope.launch {
        try {
            socket = Socket(addr, port)
            socket!!.tcpNoDelay = true
        } catch (e: Exception) {
            handler.onError(e)
            return@launch
        }
        log.printInfo("Connected to the Server")
        streamUp()
    }

    private fun streamUp() {
        enableDataHandling()
    }

    private fun handlePreparingData() : Boolean {
        try {
            val pData = PreparingDataOuterClass.PreparingData.parseDelimitedFrom(socket?.getInputStream())
            if (pData != null) {
                if (pData.password == "") {
                    this@ProjectionWorker.pData = pData
                    log.printInfo("True Password")
                    return true
                } else {
                    log.printInfo("Incorrect password")
                    handling = false
                    handler.onError(IncorrectPasswordException())
                }
            } else {
                handling = false
                handler.onError(CorruptedDataException("Corrupted pData"))
            }
        } catch(e: Exception){
            handler.onError(e)
            handling = false
        }
        return false
    }

    private fun enableDataHandling() = GlobalScope.launch(Dispatchers.IO) {
        handling = true
         if (!handlePreparingData())
            return@launch
        log.printInfo("Data Handling enabled")
        handler.onSuccess()
        try {
            while (handling) {
                val picture = HandledPictureOuterClass
                        .HandledPicture
                        .parseDelimitedFrom(socket!!.getInputStream())
                val buffer = picture.encodedPicture.toByteArray()
                saved_data.add(buffer)
            }
        } catch (e: Exception) {
            handler.onError(e)
        } finally {
            handling = false
        }
    }

    fun decode() = GlobalScope.launch(Dispatchers.IO) {
        decoder = Decoder(video!!)
        while (handling) {
            val item = saved_data.take()
            val image = withContext(Dispatchers.IO) { decoder?.decode(item) }
            if (image != null) video?.showImage(image)
        }
    }
}