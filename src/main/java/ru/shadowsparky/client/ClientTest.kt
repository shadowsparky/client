package ru.shadowsparky.client

import org.bytedeco.javacpp.opencv_highgui.cvShowImage
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
        decoder()
//        val experiment = Experiment()
        while (true) {
//            val length = 2048000
            val length = inStream!!.readInt()
//            log.printInfo("length = $length")
            if ((length > 0) and (length < 2048000)) {
                val array = ByteArray(2048000)
                inStream!!.read(array, 0, length)
                test.add(EncodedBuffer(array, length))
//                log.printInfo("$array $length")
//                Thread.sleep(1000)
//                log.printInfo("RESULT = $result")
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

    fun decoder() = Thread {
        while (true) {
            val experiment = Experiment()
            val buffer = getAvailableBuffer()
            val result = experiment.decodeFromVideo(buffer.data, 0)
            if (result != null)
                cvShowImage("Original content", result)
            log.printInfo("array: ${buffer.data} size: ${buffer.data.size} length = ${buffer.length}")
        }
    }.start()

    fun getAvailableBuffer() = test.take()

}