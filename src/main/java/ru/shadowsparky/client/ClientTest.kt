package ru.shadowsparky.client

import ru.shadowsparky.client.Extras.Companion.HOST_2
import ru.shadowsparky.client.Extras.Companion.PORT
import java.io.InputStream
import java.net.Socket

class ClientTest {
    private var socket: Socket? = null
    private var inStream: InputStream? = null
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
        inStream = socket!!.getInputStream()
        log.printInfo("Data Input Stream is UP")
        enableDataHandling()
    }

    fun enableDataHandling() = Thread {
        dataHandlingFlag = true
        log.printInfo("Handler enabled...")
//        decoder()
        val experiment = Experiment()
        experiment.startProcess()
        while (true) {
//            test.

            val array = ByteArray(240000)
            val c = inStream!!.read(array)
            log.printInfo("Readed $array")
            experiment.pushToProc(array, c)
//            test.add(EncodedBuffer(array, 1))
        }
//            val image = experiment.exFrame(array)
//            experiment.ex2_sample()
//            test.add(array)
//            decoder()
//        }
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