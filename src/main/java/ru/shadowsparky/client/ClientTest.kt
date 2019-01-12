package ru.shadowsparky.client

import ru.shadowsparky.client.Extras.Companion.HOST_2
import ru.shadowsparky.client.Extras.Companion.PORT
import java.io.ByteArrayOutputStream
import java.io.DataInputStream
import java.net.Socket

class ClientTest {
    private var socket: Socket? = null
    private var inStream: DataInputStream? = null
    private val test = Injection.provideLinkedBlockingQueue()
    private val log = Injection.provideLogger()
    private var dataHandlingFlag = false
    private var count = 0

    fun start() {
        connectToServer()
    }

    fun connectToServer() = Thread {
        socket = Socket(HOST_2, PORT)
        log.printInfo("Connected to the Server")
        streamUp()
    }.start()

    fun streamUp() {
        inStream = DataInputStream(socket!!.getInputStream())
        log.printInfo("Data Input Stream is UP")
        enableDataHandling()
    }

    fun enableDataHandling() = Thread {
        val ex = Experiment()
        val buffer = ByteArrayOutputStream()
        while(true) {
            buffer.reset()
            for (i: Int in 1..1000) {
                val array = ByteArray(1024)
                inStream!!.read(array)
                buffer.write(array)
            }
            ex.pushToProc(buffer.toByteArray())
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