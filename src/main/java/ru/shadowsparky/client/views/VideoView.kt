/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.views

import javafx.geometry.Pos
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.HBox
import javafx.scene.paint.Color
import javafx.stage.Stage
import ru.shadowsparky.client.client.Client
import ru.shadowsparky.client.controllers.VideoController
import ru.shadowsparky.client.utils.ConnectionType
import ru.shadowsparky.client.utils.ImageHandler
import tornadofx.*

class VideoView(type: ConnectionType) : View("Трансляция"), ImageHandler {
    val image: ImageView
    val controller: VideoController
    var client: Client? = null
    var stage: Stage? = null

    override val root = HBox()

    init {
        root.apply {
            alignment = Pos.CENTER
            image = imageview {
                useMaxHeight = true
                useMaxWidth = true
            }
            style {
                backgroundColor += Color.BLACK
            }
        }
        controller = VideoController(this, type)
    }

    override fun setImage(image: Image) {
        this.image.image = image
        val screenSize = controller.getScreenSize(image)
        controller.updateIncfelicity(image.width, image.height)
        this.image.fitHeight = screenSize.getHeight()
        this.image.fitWidth = screenSize.getWidth()
    }
}