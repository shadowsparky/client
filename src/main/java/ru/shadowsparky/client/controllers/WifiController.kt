package ru.shadowsparky.client.controllers

import javafx.application.Platform
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.ProgressBar
import javafx.scene.control.TextField
import javafx.scene.layout.StackPane
import ru.shadowsparky.client.utils.*
import java.io.EOFException
import java.net.ConnectException

class WifiController : Controllerable, Loadingable, Resultable {
    @FXML private lateinit var addr: TextField
    @FXML private lateinit var connect: Button
    @FXML private lateinit var loading: ProgressBar
    @FXML private lateinit var stack: StackPane
    private val log = Injection.provideLogger()
    private val launcher = Injection.provideLauncher()
    private lateinit var dialog: Dialog

    @FXML fun initialize() {
        setLoading(false)
        dialog = Dialog(stack)
        connect.setOnAction {
            setLoading(true)
            launcher.launch(ConnectionType.wifi, this, addr.text)
            connect.isDisable = true
        }
    }

    override fun onSuccess() = Platform.runLater {
        setLoading(false)
        dialog.showDialog("Очень важная информация", "Соединение успешно установлено")
        launcher.show()
    }

    override fun onError(e: Exception) = Platform.runLater {
        val error = when (e) {
            is ConnectException -> "При соединении произошла ошибка.\nСервер не найден"
            is RuntimeException -> "Соединение было разорвано.\nБыли получены битые данные"
            is EOFException -> "Произошло отключение от сервера"
            else -> "Соединение было разорвано.\nПроизошла неизвестная ошибка"
        }
        setLoading(false)
        dialog.showDialog("Ошибка", error)
        log.printInfo("${e.message}")
        connect.isDisable = false
        launcher.hide()
    }

    override fun setLoading(flag: Boolean) = Platform.runLater {
        loading.isVisible = flag
    }
}