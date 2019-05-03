/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.view

import javafx.application.Platform
import javafx.geometry.Pos
import javafx.scene.layout.VBox
import ru.shadowsparky.client.client.Client
import ru.shadowsparky.client.utils.ConnectionType
import ru.shadowsparky.client.utils.Injection
import ru.shadowsparky.client.utils.Resultable
import tornadofx.*
import java.lang.Exception

class WifiView(private val main: MainView) : View(), Resultable {
    override val root = VBox()
    private val styles = Injection.provideStyles()
    private var client: Client? = null
    private val button = styles.buttonStyle

    init {
        with(root) {
            val input = styles.wifiTextField.apply {
                text = "192.168.31.221"
            }
            this += input
            addClass(styles.wrapper)
            this += button.apply {
                action {
                    if (button.text == "Подключиться") {
                        main.video = VideoView(ConnectionType.wifi)
                        client = Client(main.video, this@WifiView, input.text)
                        client?.start()
                    } else {
                        client?.close()
                        button.text = "Подключиться"
                    }
                }
            }
            useMaxWidth = true
            alignment = Pos.CENTER
        }
    }

    override fun onSuccess() = Platform.runLater {
        main.onSuccess()
        button.text = "Отключиться"
    }

    override fun onError(e: Exception) = Platform.runLater {
        main.onError(e)
        client?.close()
        button.text = "Подключиться"
    }


}