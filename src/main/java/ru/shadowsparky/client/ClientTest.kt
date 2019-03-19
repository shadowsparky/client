package ru.shadowsparky.client

import ru.shadowsparky.client.Extras.Companion.HOST
import ru.shadowsparky.client.Extras.Companion.PORT
import ru.shadowsparky.screencast.PreparingData
import ru.shadowsparky.screencast.TransferByteArray
import java.io.ObjectInputStream
import java.net.Socket

class ClientTest(val callback: ImageCallback) {
    private var socket: Socket? = null
    private val test = Injection.provideLinkedBlockingQueue()
    private val log = Injection.provideLogger()
    private val host = Injection.provideIpV4()
    private var pData: PreparingData? = null
    private var dataHandlingFlag = false

    fun start() {
        connectToServer()
    }

    fun connectToServer() = Thread {
        socket = Socket(HOST, PORT)
        log.printInfo("Connected to the Server")
        streamUp()
    }.start()

    fun streamUp() {
        log.printInfo("Data Input Stream is UP")
        try {
            enableDataHandling()
        } catch (e: RuntimeException) {
            log.printInfo("${e.message}")
        }
    }

    @Throws(RuntimeException::class)
    fun enableDataHandling() = Thread {
        val inStream = ObjectInputStream(socket!!.getInputStream())
        val obj = inStream.readObject()
        if (obj is PreparingData) {
            if (obj.key == "key")
                pData = obj
        } else {
            RuntimeException("Corrupted data")
        }
        log.printInfo("Data Handling enabled")
        decoder()
        while(true) {
            val buf = inStream.readObject()
            if (buf is TransferByteArray) {
                log.printInfo("${buf.data} ${buf.length}")
                test.add(buf)
            } else {
                throw RuntimeException("Corrupted data")
            }
        }
    }.start()

    fun disableDataHandling() {
        log.printInfo("Handler disabled")
        dataHandlingFlag = false
    }

    fun decoder() = Thread {
        val experiment = Experiment(callback, pData!!)
        while (true) {
            val buffer = getAvailableBuffer()
            experiment.decode(buffer.data)
        }
    }.start()

    fun getAvailableBuffer() = test.take()
}