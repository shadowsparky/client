/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.projection

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.shadowsparky.client.ConnectionType
import ru.shadowsparky.client.exceptions.CorruptedDataException
import ru.shadowsparky.client.interfaces.Resultable
import ru.shadowsparky.client.mvvm.views.VideoView
import ru.shadowsparky.client.objects.Constants.LOCALHOST
import ru.shadowsparky.client.objects.Constants.PORT
import ru.shadowsparky.client.objects.Injection
import ru.shadowsparky.screencast.proto.HandledPictureOuterClass.HandledPicture as HandledPicture
import ru.shadowsparky.screencast.proto.PreparingDataOuterClass.PreparingData
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
    private lateinit var pData: PreparingData
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

    fun start() : Boolean {
        if (!connectToServer())
            return false
        upstream()
        return true
    }

    fun stop() = socket?.close()

    override fun close() {
        socket?.close()
        saved_data.clear()
        decoder = null
        video?.dispose()
        System.gc()
        System.runFinalization()
    }

    open fun connectToServer() : Boolean {
        try {
            socket = Socket(addr, port)
            socket!!.soTimeout = 5000
            socket!!.tcpNoDelay = true
        } catch (e: Exception) {
            handler.onError(e)
            return false
        }
        log.printInfo("Connected to the Server")
        return true
    }

    private fun handlePreparingData() : Boolean {
        try {
            val pData = PreparingData.parseDelimitedFrom(socket?.getInputStream())
            if (pData?.password == "") {
                this@ProjectionWorker.pData = pData
                log.printInfo("True Password")
                video = VideoView(this@ProjectionWorker, "Проецирование", type)
                decode()
                log.printInfo("Data Handling enabled")
                handler.onSuccess()
                return true
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

    private fun upstream() = GlobalScope.launch(Dispatchers.IO) {
        handling = true
        val stream = socket!!.getInputStream()
         if (!handlePreparingData())
            return@launch
        try {
            while (handling) {
                if (stream.available() > 0) {
                    val picture = HandledPicture.parseDelimitedFrom(socket!!.getInputStream())
                    val buffer = picture.encodedPicture.toByteArray()
                    saved_data.add(buffer)
                }
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