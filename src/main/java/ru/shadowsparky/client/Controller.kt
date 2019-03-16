package ru.shadowsparky.client

import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.image.Image
import javafx.scene.image.ImageView

class Controller : ImageCallback {
//    @FXML private lateinit var button: Button
    @FXML private lateinit var imageView: ImageView
    private val clientTest = ClientTest(this)

    init {
        clientTest.start()
    }

    override fun handleImage(image: Image) {
        imageView.image = image
    }

    @FXML fun initialize() {


//        val image = ex.grabVideoFile()
//        imageView.image = image
//        val image = ex.exFrame()
//        imageView.image  image
    }
}