package ru.shadowsparky.client

import javafx.fxml.FXML
import javafx.scene.control.Button

class Controller {
    @FXML private lateinit var button: Button
    private val clientTest = ClientTest()
    private val ex = Experiment()
    init {
        ex.ex2_sample()
//        clientTest.start()
//        Thread {
//            while (true) {
//                ex.mp4ToPng(clientTest.getAvailableBuffer())
//            }
//        }.start()
//        ex.mp4ToPng()
//        ex.mp4ToByteBuffer()
//        clientTest.connectToServer()
//        clientTest.streamUp()
//        clientTest.enableDataHandling()
//        Thread.sleep(5000)
//        clientTest.disableDataHandling()
//        clientTest.decoder()
    }

    @FXML fun initialize() {
        button.setOnAction {
        }
    }
}