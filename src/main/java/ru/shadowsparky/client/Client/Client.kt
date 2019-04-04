/*
 * Created by shadowsparky in 2019
 */

package ru.shadowsparky.client.Client

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.shadowsparky.client.Utils.Extras.Companion.PORT
import ru.shadowsparky.client.Utils.ConnectionHandler
import ru.shadowsparky.client.Utils.Extras
import ru.shadowsparky.client.Utils.ImageCallback
import ru.shadowsparky.client.Utils.Injection
import ru.shadowsparky.screencast.PreparingData
import ru.shadowsparky.screencast.TransferByteArray
import java.io.*
import java.net.Socket
import java.net.SocketException

class Client(
        private val callback: ImageCallback,
        private val handler: ConnectionHandler,
        private val addr: String,
        private val port: Int = PORT
) {
    private var socket: Socket? = null
    private val test = Injection.provideLinkedBlockingQueue()
    private val log = Injection.provideLogger()
    private var inStream: ObjectInputStream? = null
    private var decoder: Decoder? = null
    var handling: Boolean = false
        set(value) {
            if (value) {
                log.printInfo("Handling enabled")
            } else {
                socket?.close()
                inStream?.close()
                
            }
            field = value
        }

    fun start() {
        connectToServer()
    }

    private fun connectToServer() = GlobalScope.launch {
        try {
            socket = Socket(addr, port)
            inStream = ObjectInputStream(BufferedInputStream(socket!!.getInputStream()))
//            inDataStream = DataInputStream(BufferedInputStream(socket!!.getInputStream()))
            socket!!.tcpNoDelay = true
        } catch (e: Exception) {
            handler.onError(e)
            return@launch
        }
        log.printInfo("Connected to the Server")
        streamUp()
    }

    private fun streamUp() {
        log.printInfo("Data Input Stream is UP")
        try {
            enableDataHandling()
        } catch (e: RuntimeException) {
            log.printInfo("${e.message}")
        }
    }

    @Throws(RuntimeException::class)
    private fun enableDataHandling() = GlobalScope.launch(Dispatchers.IO) {
        handling = true
        val obj = inStream!!.readObject()
        if (obj is PreparingData) {
            if (obj.key == "key")
                log.printInfo("True Password")
            else {
                log.printInfo("Incorrect password")
                handling = false
                return@launch
            }
        } else {
            handling = false
            log.printInfo("Handling disabled by: Preparing Data Not Found. Corrupted data")
            return@launch
        }
        log.printInfo("Data Handling enabled")
        decoder()
        handler.onSuccess()
        try {
            decoder = Decoder(callback)
            while (handling) {
                val buf = inStream!!.readObject()
                if (buf is TransferByteArray) {
                    test.add(buf)
                } else
                    throw RuntimeException("Corrupted Data")
            }
        } catch (e: SocketException) {
            log.printInfo("Handling disabled by: SocketException. ${e.message}")
        } catch (e: RuntimeException) {
            log.printInfo("Handling disabled by: RuntimeException. ${e.message}")
            handler.onError(e)
        } catch (e: EOFException) {
            log.printInfo("Handling disabled by: EOFException. ${e.message}")
            handler.onError(e)
        } catch (e: OptionalDataException) {
            log.printInfo("Handling disabled by: OptionalDataException. ${e.message}")
        } finally {
            handling = false
        }
    }

    private fun decoder() = GlobalScope.launch(Dispatchers.IO) {
        while (handling) {
            val buffer = getAvailableBuffer()
            decoder?.decode(buffer.data)
        }
    }

    private fun getAvailableBuffer() = test.take()!!
}