/*
 * Created by shadowsparky in 2019
 */

package ru.shadowsparky.client.Client

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.shadowsparky.client.Utils.Extras.Companion.PORT
import ru.shadowsparky.client.Utils.ConnectionHandler
import ru.shadowsparky.client.Utils.ImageCallback
import ru.shadowsparky.client.Utils.Injection
import ru.shadowsparky.screencast.PreparingData
import ru.shadowsparky.screencast.TransferByteArray
import java.io.BufferedInputStream
import java.io.EOFException
import java.io.ObjectInputStream
import java.net.Socket
import java.net.SocketException

class Client(
        private val callback: ImageCallback,
        private val handler: ConnectionHandler,
        private val addr: String
) {
    private var socket: Socket? = null
    private val test = Injection.provideLinkedBlockingQueue()
    private val log = Injection.provideLogger()
    private var pData: PreparingData? = null
    private var inStream: ObjectInputStream? = null
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
            socket = Socket(addr, 1488)
            inStream = ObjectInputStream(BufferedInputStream(socket!!.getInputStream()))
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
                val buf = inStream!!.readObject()
                if (buf is TransferByteArray) {
                    test.add(buf)
                } else {
                    throw RuntimeException("Corrupted Data")
                }
                log.printInfo("${buf.data} ${buf.length}")
            }
        } catch (e: SocketException) {
            log.printInfo("Handling disabled by: SocketException. ${e.message}")
        } catch (e: RuntimeException) {
            log.printInfo("Handling disabled by: RuntimeException. ${e.message}")
            handler.onError(e)
        } catch (e: EOFException) {
            log.printInfo("Handling disabled by: EOFException. ${e.message}")
            handler.onError(e)
        } finally {
            handling = false
        }
    }

    private fun decoder() = GlobalScope.launch(Dispatchers.IO) {
        val experiment = Decoder(callback, pData!!)
        while (handling) {
            val buffer = getAvailableBuffer()
            experiment.decode(buffer.data)
        }
    }

    private fun getAvailableBuffer() = test.take()!!
}