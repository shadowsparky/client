package ru.shadowsparky.client.Controllers

import javafx.fxml.FXML
import javafx.scene.image.Image
import javafx.scene.layout.*
import javafx.stage.WindowEvent
import ru.shadowsparky.client.Client.Client
import ru.shadowsparky.client.Utils.ImageCallback

class VideoController : ImageCallback {
    private var client: Client? = null
    @FXML private lateinit var videoPane: GridPane

    override fun handleImage(image: Image) {
        val bImage = BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize((1280).toDouble(), (720).toDouble(), true, true, true, false))
        val background = Background(bImage)
        videoPane.background = background
    }

    fun onDestroy(event: WindowEvent) {

    }

    fun attachClient(client: Client) {
        this.client = client
    }

    fun start() {
        client!!.start()
    }

}