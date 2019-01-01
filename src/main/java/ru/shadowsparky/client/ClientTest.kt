package ru.shadowsparky.client

import ru.shadowsparky.client.Extras.Companion.HOST_2
import ru.shadowsparky.client.Extras.Companion.PORT
import java.io.DataInputStream
import java.net.Socket

class ClientTest {
    private var socket: Socket? = null
    private var inStream: DataInputStream? = null
    private val test = Injection.provideLinkedBlockingQueue()
    private val log = Injection.provideLogger()
    private var dataHandlingFlag = false

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
        dataHandlingFlag = true
        log.printInfo("Handler enabled...")
        val experiment = Experiment()
        while (true) {
            val length = inStream!!.readInt()
            log.printInfo("length = $length")
            if (length > 0) {
                val array = ByteArray(length)
                inStream!!.read(array)
                log.printInfo(array.toString())
                experiment.exFrame(array)
            }
//            val image = experiment.exFrame(array)
//            experiment.ex2_sample()
//            test.add(array)
//            decoder()
        }
    }.start()

    fun disableDataHandling() {
        log.printInfo("Handler disabled")
        dataHandlingFlag = false
    }

    fun decoder() = log.printInfo(String(test.take()))

    fun getAvailableBuffer() = test.take()

}