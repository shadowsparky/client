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
import java.io.BufferedInputStream
import java.io.ObjectInputStream
import java.net.Socket

class Client(val callback: ImageCallback, val handler: ConnectionHandler) {
    private var socket: Socket? = null
    private val test = Injection.provideLinkedBlockingQueue()
    private val log = Injection.provideLogger()
    private var pData: PreparingData? = null
    private var handling: Boolean = false

    fun start() {
        connectToServer()
    }

    private fun connectToServer() = Thread {
        try {
            socket = Socket("192.168.31.221", PORT)
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

    private fun setHanding(flag: Boolean) {
        handling = flag
        if (handling) {
            log.printInfo("Handling enabled")
        } else {
            log.printInfo("Handling disabled")
        }
    }

    @Throws(RuntimeException::class)
    private fun enableDataHandling() = Thread {
        setHanding(true)
        val inStream = ObjectInputStream(BufferedInputStream(socket!!.getInputStream()))
        val obj = inStream.readObject()
        if (obj is PreparingData) {
            if (obj.key == "key")
                pData = obj
        } else {
            setHanding(false)
            log.printInfo("Handling disabled. Corrupted Data")
        }
        log.printInfo("Data Handling enabled")
        decoder()
        handler.onSuccess()
        while (handling) {
            val buf = inStream.readObject()
            if (buf is TransferByteArray) {
                log.printInfo("${buf.data} ${buf.length}")
                test.add(buf)
            } else if (buf is DisableHandling) {
                if (buf.action == "disable")
                    setHanding(false)
            }
        }
    }.start()

    private fun decoder() = Thread {
        val experiment = Decoder(callback, pData!!)
        while (true) {
            val buffer = getAvailableBuffer()
            experiment.decode(buffer.data)
        }
    }.start()

    private fun getAvailableBuffer() = test.take()!!
}