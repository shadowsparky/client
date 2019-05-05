/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.views

import javafx.application.Platform
import javafx.geometry.Pos
import javafx.scene.control.ProgressBar
import javafx.scene.control.ProgressIndicator
import javafx.scene.control.ProgressIndicator.INDETERMINATE_PROGRESS
import javafx.scene.layout.StackPane
import javafx.scene.layout.VBox
import ru.shadowsparky.client.controllers.AdbController
import ru.shadowsparky.client.utils.*
import tornadofx.*
import java.awt.Color

class AdbView : BaseView() {
    override val root = styles.getDefaultStackPane()
    var input = styles.getDefaultList()
    private val controller: AdbController = AdbController(this)

    fun updateDevices() = Platform.runLater {
        input.items.clear()
        input = styles.getDefaultList().apply {
            controller.updateDevices()
        }
    }

    init {
        dialog = Dialog(root)
        with(root) {
            vbox {
                this += styles.getLabel("Выберите устройство")
                this += input
                addClass(styles.wrapper)
                this += styles.getDefaultButton().apply {
                    action {
                        controller.startProjection()
                    }
                }
                addClass(styles.wrapper)
                this += styles.getDefaultButton().apply {
                    text = "Справка"
                    action {
                        controller.showHelp()
                    }
                    style {
                        backgroundColor += styles.defaultColor
                    }
                }
//                addClass(styles.wrapper)
                this += ProgressBar().apply {
                    progress = INDETERMINATE_PROGRESS
                }
                useMaxWidth = true
                alignment = Pos.CENTER
            }
        }
    }
}