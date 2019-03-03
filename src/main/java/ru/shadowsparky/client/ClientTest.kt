package ru.shadowsparky.client

import ru.shadowsparky.client.Extras.Companion.HOST
import ru.shadowsparky.client.Extras.Companion.PORT
import java.io.BufferedInputStream
import java.net.Socket

class ClientTest {
    private var socket: Socket? = null
    private var inStream: BufferedInputStream? = null
    private val test = Injection.provideLinkedBlockingQueue()
    private val log = Injection.provideLogger()
    private var dataHandlingFlag = false
    private var count = 0

    fun start() {
        connectToServer()
    }

    fun connectToServer() = Thread {
        socket = Socket(HOST, PORT)
        log.printInfo("Connected to the Server")
        streamUp()
    }.start()

    fun streamUp() {
        inStream = BufferedInputStream(socket!!.getInputStream())
        log.printInfo("Data Input Stream is UP")
        enableDataHandling()
        decoder()
    }

    fun enableDataHandling() = Thread {
        val ex = Experiment()
        while(true) {
            val buffer = ByteArray(1024)
            if (inStream!!.read(buffer, 0, 1024) != -1) {
                test.add(buffer)
            } else {
                log.printError("Reading error; read is -1")
            }
        }
    }.start()

    fun disableDataHandling() {
        log.printInfo("Handler disabled")
        dataHandlingFlag = false
    }

    fun decoder() = Thread {
//        val experiment = Experiment()
//        experiment.startProcess()
//        while (true) {
//            val buffer = getAvailableBuffer()
//            experiment.pushToProc(buffer.data)
//        }
    }.start()

    fun getAvailableBuffer() = test.take()
}