/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.views

import javafx.application.Platform
import javafx.geometry.Pos
import javafx.scene.layout.VBox
import ru.shadowsparky.client.controllers.AdbController
import ru.shadowsparky.client.utils.*
import tornadofx.*

class AdbView : BaseView() {
    override val root = VBox()
    var input = styles.defaultList
    private val controller: AdbController = AdbController(this)

    fun updateDevices() = Platform.runLater {
        input.items.clear()
        input = styles.defaultList.apply {
            controller.updateDevices()
        }
    }

    init {
        with(root) {
            this += input
            addClass(styles.wrapper)
            this += styles.buttonStyle.apply {
                action {
                    controller.startProjection()
                }
            }
            addClass(styles.wrapper)
            this += Styles().buttonStyle.apply {
                text = "Справка"
                style {
                    backgroundColor += styles.defaultColor
                }
            }
            useMaxWidth = true
            alignment = Pos.CENTER
        }
    }
}