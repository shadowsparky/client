/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.view

import com.jfoenix.controls.JFXButton
import com.jfoenix.controls.JFXTextField
import javafx.geometry.Pos
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import ru.shadowsparky.client.client.Client
import ru.shadowsparky.client.utils.ConnectionType
import tornadofx.*

class WifiView(view: MainView) : View() {
    override val root = VBox()

    init {
        with(root) {
            val input = JFXTextField().apply {
                maxWidth = 300.0
                minWidth = 300.0
                minHeight = 40.0
                style {
                    fontSize = Dimension(16.0, Dimension.LinearUnits.px)
                    endMargin = Dimension(16.0, Dimension.LinearUnits.px)
                }
            }
            this += input
            vbox {
                minHeight = 10.0
            }
            this += JFXButton().apply {
                maxWidth = 300.0
                minWidth = 300.0
                minHeight = 50.0
                action {
                    view.video = VideoView(ConnectionType.wifi)
                    val client = Client(view.video, view, input.text)
                    client.start()
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