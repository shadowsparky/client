/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.view

import com.jfoenix.controls.JFXButton
import com.jfoenix.controls.JFXListView
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import ru.shadowsparky.client.client.Client
import ru.shadowsparky.client.controllers.AdbController
import ru.shadowsparky.client.utils.ConnectionType
import ru.shadowsparky.client.utils.Extras
import ru.shadowsparky.client.utils.Injection
import ru.shadowsparky.client.utils.Parser
import ru.shadowsparky.client.utils.adb.ADBStatus
import tornadofx.*

class AdbView(val view: MainView) : View() {
    override val root = VBox()
    val input = JFXListView<Label>()
    private val adb = Injection.provideAdb()
    private val controller: AdbController

    fun updateDevices() {
        input.items.clear()
        input.apply {
            maxWidth = 300.0
            minWidth = 300.0
            minHeight = 40.0
            controller.updateDevices()
        }
    }

    init {
        controller = AdbController(this)
        with(root) {
            this += input
            vbox {
                minHeight = 10.0
            }
            this += JFXButton().apply {
                maxWidth = 300.0
                minWidth = 300.0
                minHeight = 50.0
                action {
                    view.video = VideoView(ConnectionType.adb)
                    val client = Client(view.video, view, "127.0.0.1", Extras.FORWARD_PORT)
                    val strDevice = input.selectionModel?.selectedItem?.text
                    if (strDevice != null) {
                        val device = Parser.deviceToStr(strDevice)
                        if (device != null) {
                            adb.forwardPort(device.id)
                            client.start()
                        }
                    }
                }
                style {
                    buttonType = JFXButton.ButtonType.RAISED
                    backgroundColor += Color.BLUE
                }
            }
            useMaxWidth = true
            alignment = Pos.CENTER
        }
    }

}