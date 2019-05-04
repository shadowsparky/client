/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.views

import javafx.geometry.Pos
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.HBox
import javafx.stage.Stage
import ru.shadowsparky.client.client.Client
import ru.shadowsparky.client.utils.ConnectionType
import ru.shadowsparky.client.controllers.VideoController
import ru.shadowsparky.client.utils.ImageCallback
import tornadofx.View
import tornadofx.imageview
import tornadofx.useMaxHeight
import tornadofx.useMaxWidth

class VideoView(type: ConnectionType) : View(), ImageCallback {
    val image: ImageView
    private val controller: VideoController
    var client: Client? = null
    var stage: Stage? = null

    override val root = HBox()

    init {
        with(root){
            alignment = Pos.CENTER
            image = imageview {
                useMaxHeight = true
                useMaxWidth = true
            }
        }
        controller = VideoController(this, type)
    }

    override fun handleImage(image: Image) {
        this.image.image = image
        val screenSize = controller.getScreenSize(image)
        controller.updateIncfelicity(image.width, image.height)
        this.image.fitHeight = screenSize.getHeight()
        this.image.fitWidth = screenSize.getWidth()
    }
}