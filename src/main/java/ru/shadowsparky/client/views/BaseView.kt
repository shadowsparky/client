/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.views

import javafx.application.Platform
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import javafx.scene.Parent
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.layout.StackPane
import javafx.stage.StageStyle
import jfxtras.styles.jmetro8.JMetro
import ru.shadowsparky.client.utils.ConnectionType
import ru.shadowsparky.client.utils.Dialog
import ru.shadowsparky.client.utils.Injection
import ru.shadowsparky.client.utils.Resultable
import ru.shadowsparky.client.utils.exceptions.CorruptedDataException
import ru.shadowsparky.client.utils.exceptions.IncorrectPasswordException
import tornadofx.View
import java.io.EOFException
import java.net.ConnectException

abstract class BaseView : View(""), Resultable {
    abstract override val root: StackPane

    protected val styles = Injection.provideStyles()
    var video: VideoView? = null
    lateinit var dialog: Dialog
    var mButtonStatus = SimpleBooleanProperty(false)
    var mButtonText = SimpleStringProperty("Подключиться")

    protected fun setStyle() {
        JMetro(JMetro.Style.DARK).applyTheme(root)
    }

    override fun onSuccess() = Platform.runLater {
        video!!.stage = video!!.openWindow(StageStyle.UNDECORATED)?.apply {
            isFullScreen = true
        }
        video!!.stage?.addEventFilter(KeyEvent.KEY_PRESSED) {
            if (it.code == KeyCode.ESCAPE) {
                video!!.client?.close()
            }
        }
        if (video?.controller?.type == ConnectionType.adb)
            video?.controller?.enableADBActions()
        mButtonStatus.set(true)
    }

    override fun onError(e: Exception) = Platform.runLater {
        mButtonStatus.set(false)
        video?.stage?.close()
        log.info("onError: $e")
        val error = when (e) {
            is ConnectException -> "При соединении произошла ошибка.\nСервер не найден"
            //is CorruptedDataException -> "Соединение было разорвано."
            is EOFException -> "Произошло отключение от сервера"
            is IncorrectPasswordException -> "При соединении с сервером произошла ошибка. Вы ввели неправильный пароль"
            else -> "Соединение было разорвано"
        }
        dialog.showDialog("Ошибка", error, true)
    }
}