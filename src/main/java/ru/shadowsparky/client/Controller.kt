package ru.shadowsparky.client

import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.image.ImageView

class Controller {
    @FXML private lateinit var button: Button
    @FXML private lateinit var imageView: ImageView
    private val clientTest = ClientTest()
    private val ex = Experiment()
    init {
        clientTest.start()

    }

    @FXML fun initialize() {
        button.setOnAction {
        }
//        val image = ex.grabVideoFile()
//        imageView.image = image
//        val image = ex.exFrame()
//        imageView.image  image
    }
}