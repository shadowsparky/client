/*
 * Created by shadowsparky in 2019
 */

package ru.shadowsparky.client.client

import com.google.protobuf.InvalidProtocolBufferException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.shadowsparky.client.entities.TransferBuffer
import ru.shadowsparky.client.utils.ConnectionType
import ru.shadowsparky.client.utils.Extras.Companion.PORT
import ru.shadowsparky.client.utils.ImageHandler
import ru.shadowsparky.client.utils.Injection
import ru.shadowsparky.client.utils.Resultable
import ru.shadowsparky.client.utils.exceptions.CorruptedDataException
import ru.shadowsparky.client.utils.exceptions.IncorrectPasswordException
import ru.shadowsparky.screencast.proto.HandledPictureOuterClass
import ru.shadowsparky.screencast.proto.PreparingDataOuterClass
import java.io.Closeable
import java.io.EOFException
import java.io.OptionalDataException
import java.net.Socket
import java.net.SocketException
import java.util.concurrent.LinkedBlockingQueue

class Client(
        private val callback: ImageHandler,
        private val handler: Resultable,
        val addr: String,
        private val port: Int = PORT
) : Closeable {

    override fun close() {
        socket?.close()
        saved_data.clear()
        decoder = null
        System.gc()
    }

    private val type: ConnectionType

    init {
        type = if (addr == "127.0.0.1") ConnectionType.adb else ConnectionType.wifi
    }

    private var socket: Socket? = null
    private val log = Injection.provideLogger()
    private lateinit var pData: PreparingDataOuterClass.PreparingData
    private var saved_data = LinkedBlockingQueue<TransferBuffer>()
    private var decoder: Decoder? = null
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

    fun start() {
        connectToServer()
    }

    private fun connectToServer() = GlobalScope.launch {
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
                    this@Client.pData = pData
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
        } catch(e: InvalidProtocolBufferException) {
            // ignore
        }
        return false
    }

    private fun enableDataHandling() = GlobalScope.launch(Dispatchers.Unconfined) {
        handling = true
         if (!handlePreparingData())
            return@launch
        log.printInfo("Data Handling enabled")
        handler.onSuccess()
        decode()
        try {
            while (handling) {
                val picture = HandledPictureOuterClass
                        .HandledPicture
                        .parseDelimitedFrom(socket!!.getInputStream())
                val buffer = TransferBuffer(picture.encodedPicture.toByteArray(), picture.flags)
                saved_data.add(buffer)
            }
        } catch (e: SocketException) {
            log.printInfo("Handling disabled by: SocketException. ${e.message}")
            handler.onError(e)
        } catch (e: RuntimeException) {
            log.printInfo("Handling disabled by: RuntimeException. ${e.message}")
            handler.onError(e)
        } catch (e: EOFException) {
            log.printInfo("Handling disabled by: EOFException. ${e.message}")
            handler.onError(e)
        } catch (e: OptionalDataException) {
            log.printInfo("Handling disabled by: OptionalDataException. ${e.message}")
            handler.onError(e)
        } finally {
            handling = false
        }
    }

    fun decode() = GlobalScope.launch(Dispatchers.IO) {
        decoder = Decoder()
//        Decoder().use {
            while (handling) {
                val item = saved_data.take()
                val image = withContext(Dispatchers.IO) { decoder?.decode(item.data, saved_data.size) }
                if (image != null) callback.setImage(image)
            }
//        }
    }
}