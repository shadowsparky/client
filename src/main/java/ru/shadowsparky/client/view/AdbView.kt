/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.view

import javafx.application.Platform
import javafx.geometry.Pos
import javafx.scene.layout.VBox
import ru.shadowsparky.client.client.Client
import ru.shadowsparky.client.controllers.AdbController
import ru.shadowsparky.client.utils.*
import ru.shadowsparky.client.views.VideoView
import tornadofx.*

class AdbView(val view: MainView) : Resultable, View() {
    override val root = VBox()
    val styles = Injection.provideStyles()
    var input = styles.defaultList
    private val adb = Injection.provideAdb()
    private val controller: AdbController
    private var client: Client? = null
    private val button = styles.buttonStyle
    override fun onSuccess() = Platform.runLater {
        view.onSuccess()
        button.text = "Отключиться"
    }

    override fun onError(e: Exception) = Platform.runLater {
        view.onError(e)
        client?.close()
        button.text = "Подключиться"
    }


    fun updateDevices() = Platform.runLater {
        input.items.clear()
        input = styles.defaultList.apply {
            controller.updateDevices()
        }
    }

    init {
        controller = AdbController(this)
        with(root) {
            this += input
            addClass(styles.wrapper)
            this += button.apply {
                action {
                    if (button.text == "Подключиться") {
                        view.video = VideoView(ConnectionType.adb)
                        client = Client(view.video, this@AdbView, "127.0.0.1", Extras.FORWARD_PORT)
                        val strDevice = input.selectionModel?.selectedItem?.text
                        if (strDevice != null) {
                            val device = Parser.deviceToStr(strDevice)
                            if (device != null) {
                                adb.forwardPort(device.id)
                                client?.start()
                            }
                        }
                    } else {
                        client?.close()
                        button.text = "Подключиться"
                    }
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