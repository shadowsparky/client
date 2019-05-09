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
import ru.shadowsparky.client.utils.exceptions.ProjectionAlreadyStartedException
import tornadofx.View
import tornadofx.onChange
import java.io.EOFException
import java.net.ConnectException

abstract class BaseView : View(""), Resultable {
    abstract override val root: StackPane

    protected val styles = Injection.provideStyles()
    var video: CanvasVideoFrame? = null
    lateinit var dialog: Dialog
    var mButtonStatus = SimpleBooleanProperty(false)
    var loading = SimpleBooleanProperty(false)
    var mButtonText = SimpleStringProperty("Подключиться")

    init {
        mButtonStatus.onChange {
            loading.set(it)
        }
    }

    override fun onSuccess() = Platform.runLater {
//        video!!.stage = video!!.openWindow()?.apply {
//            isFullScreen = true
//        }
//        video!!.stage?.addEventFilter(KeyEvent.KEY_PRESSED) {
//            if (it.code == KeyCode.ESCAPE) {
//                video!!.client?.close()
//            }
//        }
//        if (video?.controller?.type == ConnectionType.adb)
//            video?.controller?.enableADBActions()
        mButtonStatus.set(true)
    }

    override fun onError(e: Exception) = Platform.runLater {
        mButtonStatus.set(false)
        video?.dispose()
        log.info("onError: $e")
        val error = when (e) {
            is ConnectException -> "При соединении произошла ошибка.\nСервер не найден"
            //is CorruptedDataException -> "Соединение было разорвано."
            is EOFException -> "Произошло отключение от сервера"
            is ProjectionAlreadyStartedException -> "При соединении произошла ошибка. \nВы не отключились от предыдущего соединения"
            is IncorrectPasswordException -> "При соединении с сервером произошла ошибка. Вы ввели неправильный пароль"
            else -> "Соединение было разорвано"
        }
        dialog.showDialog("Ошибка", error, true)
    }
}