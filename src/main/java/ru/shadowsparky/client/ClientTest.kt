package ru.shadowsparky.client

import ru.shadowsparky.client.Extras.Companion.HOST
import ru.shadowsparky.client.Extras.Companion.PORT
import java.io.BufferedInputStream
import java.io.DataInputStream
import java.net.Socket
import java.nio.ByteBuffer

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
        val inStream = DataInputStream(socket!!.getInputStream())
//        val inS = BufferedInputStream(socket!!.getInputStream())
        while(true) {
            if (inStream.available() > 0) {
                val length = inStream.readInt()
                log.printInfo(length.toString())
            }
        }
    }.start()

    fun disableDataHandling() {
        log.printInfo("Handler disabled")
        dataHandlingFlag = false
    }

    fun decoder() = Thread {
        val experiment = Experiment()
        while (true) {
            val buffer = getAvailableBuffer()
            experiment.decode(buffer)
        }
    }.start()

    fun getAvailableBuffer() = test.take()
}