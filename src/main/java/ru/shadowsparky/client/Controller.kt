package ru.shadowsparky.client

import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.*

class Controller : ImageCallback {
//    @FXML private lateinit var button: Button
    @FXML private lateinit var imageView: ImageView
    @FXML private lateinit var main: GridPane
    private val clientTest = ClientTest(this)

    init {
        clientTest.start()
    }

    override fun handleImage(image: Image) {
        val bImage = BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize((1280).toDouble(), (720).toDouble(), true, true, true, false))
        val background = Background(bImage)
        main.background = background
    }

    @FXML fun initialize() {


//        val image = ex.grabVideoFile()
//        imageView.image = image
//        val image = ex.exFrame()
//        imageView.image  image
    }
}