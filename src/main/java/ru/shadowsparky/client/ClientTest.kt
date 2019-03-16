package ru.shadowsparky.client

import ru.shadowsparky.client.Extras.Companion.HOST
import ru.shadowsparky.client.Extras.Companion.PORT
import ru.shadowsparky.screencast.TransferByteArray
import java.io.ObjectInputStream
import java.lang.RuntimeException
import java.net.Socket

class ClientTest(val callback: ImageCallback) {
    private var socket: Socket? = null
    private val test = Injection.provideLinkedBlockingQueue()
    private val log = Injection.provideLogger()
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
        enableDataHandling()
        decoder()
    }

    fun enableDataHandling() = Thread {
        val inStream = ObjectInputStream(socket!!.getInputStream())
        log.printInfo("Data Handling enabled")
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
        val experiment = Experiment(callback)
        while (true) {
            val buffer = getAvailableBuffer()
            experiment.decode(buffer.data)
        }
    }.start()

    fun getAvailableBuffer() = test.take()
}