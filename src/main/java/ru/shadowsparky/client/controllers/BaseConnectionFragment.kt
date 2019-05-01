/*
 * Created by shadowsparky in 2019
 */

package ru.shadowsparky.client.controllers

import javafx.application.Platform
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.ProgressBar
import javafx.scene.layout.StackPane
import ru.shadowsparky.client.utils.*
import ru.shadowsparky.client.utils.exceptions.CorruptedDataException
import ru.shadowsparky.client.utils.exceptions.IncorrectPasswordException
import java.io.EOFException
import java.net.ConnectException

abstract class BaseConnectionFragment : Controllerable, Loadingable, Resultable {
    @FXML protected lateinit var connect: Button
    @FXML protected lateinit var loading: ProgressBar
    @FXML protected lateinit var stack: StackPane
    protected val log = Injection.provideLogger()
    protected val launcher = Injection.provideLauncher()
    protected lateinit var dialog: Dialog

    fun init() {
        setLoading(false)
        dialog = Dialog(stack)
        loading.progress = ProgressBar.INDETERMINATE_PROGRESS
    }

    override fun onSuccess() = Platform.runLater {
        setLoading(false)
        launcher.show()
    }

    override fun onError(e: Exception) = Platform.runLater {
        val error = when (e) {
            is ConnectException -> "При соединении произошла ошибка.\nСервер не найден"
            is CorruptedDataException -> "Соединение было разорвано.\nБыли получены битые данные"
            is EOFException -> "Произошло отключение от сервера"
            is IncorrectPasswordException -> "При соединении с сервером произошла ошибка. Вы ввели неправильный пароль"
            else -> "Соединение было разорвано"

        }
        setLoading(false)
        dialog.showDialog("Ошибка", error)
        log.printInfo("${e.message}")
        connect.isDisable = false
        launcher.hide()
        connect.text = "Подключиться"
    }

    override fun setLoading(flag: Boolean) = Platform.runLater {
        loading.isVisible = flag
    }
}