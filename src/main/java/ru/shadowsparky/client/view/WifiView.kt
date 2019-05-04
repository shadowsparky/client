/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.view

import javafx.application.Platform
import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Pos
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.layout.VBox
import ru.shadowsparky.client.client.Client
import ru.shadowsparky.client.controllers.WifiController
import ru.shadowsparky.client.utils.ConnectionType
import ru.shadowsparky.client.utils.Injection
import ru.shadowsparky.client.utils.Resultable
import ru.shadowsparky.client.views.WifiView
import tornadofx.*
import java.lang.Exception

class WifiView(val main: MainView) : View(), Resultable {
    override val root = VBox()
    val styles = Injection.provideStyles()
    var client: Client? = null
    var mInputText = SimpleStringProperty("192.168.31.221")
    var mButtonText = SimpleStringProperty("Подключиться")
    private var controller: WifiController = WifiController(WifiView())

    init {
        with(root) {
            this += styles.wifiTextField.apply {
                bind(mInputText)
            }
            addClass(styles.wrapper)
            this += styles.buttonStyle.apply {
                bind(mButtonText)
                action { controller.startProjection() }
            }
            useMaxWidth = true
            alignment = Pos.CENTER
        }
    }

    override fun onSuccess() = Platform.runLater {
        main.onSuccess()
        mButtonText.set("Отключиться")
    }

    override fun onError(e: Exception) = Platform.runLater {
        main.onError(e)
        client?.close()
        mButtonText.set("Подключиться")
    }
}