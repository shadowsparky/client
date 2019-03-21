/*
 * Created by shadowsparky in 2019
 */

package ru.shadowsparky.client.Client

import ru.shadowsparky.client.Extras.Companion.PORT
import ru.shadowsparky.client.Utils.ConnectionHandler
import ru.shadowsparky.client.Utils.ImageCallback
import ru.shadowsparky.client.Utils.Injection
import ru.shadowsparky.screencast.DisableHandling
import ru.shadowsparky.screencast.PreparingData
import ru.shadowsparky.screencast.TransferByteArray
import java.io.*
import java.net.Socket
import java.net.SocketException

class Client(
        private val callback: ImageCallback,
        private val handler: ConnectionHandler
) {
    private var socket: Socket? = null
    private val test = Injection.provideLinkedBlockingQueue()
    private val log = Injection.provideLogger()
    private var pData: PreparingData? = null
    var handling: Boolean = false
        set(value) {
            if (value) {
                log.printInfo("Handling enabled")
            } else {
                socket?.close()
            }
            field = value
        }

    fun start() {
        connectToServer()
    }

    private fun connectToServer() = Thread {
        try {
            socket = Socket("192.168.31.221", PORT)
            socket!!.tcpNoDelay = true
        } catch (e: Exception) {
            handler.onError(e)
            return@Thread
        }
        log.printInfo("Connected to the Server")
        streamUp()
    }.start()

    private fun streamUp() {
        log.printInfo("Data Input Stream is UP")
        try {
            enableDataHandling()
        } catch (e: RuntimeException) {
            log.printInfo("${e.message}")
        }
    }

    @Throws(RuntimeException::class)
    private fun enableDataHandling() = Thread {
        handling = true
        val inStream = ObjectInputStream(BufferedInputStream(socket!!.getInputStream()))
        val obj = inStream.readObject()
        if (obj is PreparingData) {
            if (obj.key == "key")
                pData = obj
        } else {
            handling = false
            log.printInfo("Handling disabled by: Preparing Data Not Found. Corrupted data")
        }
        log.printInfo("Data Handling enabled")
        decoder()
        handler.onSuccess()
        try {
            while (handling) {
                val buf = inStream.readObject()
                if (buf is TransferByteArray) {
                    log.printInfo("${buf.data} ${buf.length}")
                    test.add(buf)
                } else {
                    throw RuntimeException("Corrupted Data")
                }
            }
        } catch (e: SocketException) {
            handling = false
            log.printInfo("Handling disabled by: SocketException. ${e.message}")
            return@Thread
        } catch (e: RuntimeException) {
            handling = false
            log.printInfo("Handling disabled by: RuntimeException. ${e.message}")
            handler.onError(e)
        }
    }.start()

    private fun decoder() = Thread {
        val experiment = Decoder(callback, pData!!)
        while (handling) {
            val buffer = getAvailableBuffer()
            experiment.decode(buffer.data)
        }
    }.start()

    private fun getAvailableBuffer() = test.take()!!
}