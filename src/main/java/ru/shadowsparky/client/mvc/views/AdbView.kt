/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.mvc.views

import javafx.application.Platform
import javafx.geometry.Pos
import ru.shadowsparky.client.mvc.controllers.AdbController
import ru.shadowsparky.client.utils.*
import ru.shadowsparky.client.utils.objects.Injection
import tornadofx.*

class AdbView : BaseView() {
    override val root = styles.getDefaultStackPane()
    var input = styles.getDefaultList()
    private val controller = Injection.provideAdbController(this)
    var deviceAddr: String? = null

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
                this += input.apply {
                    selectionModel.selectedItemProperty().addListener { obs, ov, nv ->
                        deviceAddr = nv.text
                    }
                }
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
//                this += ProgressBar().apply {
//                    progress = INDETERMINATE_PROGRESS
//                }
                useMaxWidth = true
                alignment = Pos.CENTER
            }
        }
    }
}