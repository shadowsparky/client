package ru.shadowsparky.client

import ru.shadowsparky.client.Extras.Companion.HOST
import ru.shadowsparky.client.Extras.Companion.PORT
import ru.shadowsparky.screencast.TransferByteArray
import java.io.ObjectInputStream
import java.net.Socket

class ClientTest {
    private var socket: Socket? = null
    private var inStream: ObjectInputStream? = null
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
//            if (inStream.available() > 0) {
            val buf = inStream.readObject()
            if (buf is TransferByteArray) {
                log.printInfo("${buf.data} ${buf.length}")
                test.add(buf)
            } else {
                buf.toString()
            }
//                val buf = inStream.readObject() as TransferByteArray
//                log.printInfo("${buf.data} ${buf.length}")
//            }
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
            experiment.decode(buffer.data)
        }
    }.start()

    fun getAvailableBuffer() = test.take()
}