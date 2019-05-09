/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.views

import javafx.geometry.Pos
import javafx.scene.CacheHint
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
import javafx.scene.effect.Lighting



class VideoView(type: ConnectionType) : View("Трансляция"), ImageHandler {
    override fun setImage(image: Image) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    //    val image: ImageView
//    val controller: VideoController
//    var client: Client? = null
//    var stage: Stage? = null
//
    override val root = HBox()
//
//    init {
//        root.apply {
//            alignment = Pos.CENTER
//            image = imageview {
//                useMaxHeight = true
//                useMaxWidth = true
//            }
//            style {
//                backgroundColor += Color.BLACK
//            }
//        }
//        controller = VideoController(this, type)
//        val lighting = Lighting()
//        image.cacheHint = CacheHint.SPEED
//        image.effect = lighting

}