/*
 * Created by shadowsparky in 2019
 */

package ru.shadowsparky.client.Controllers

import com.jfoenix.controls.JFXButton
import com.jfoenix.controls.JFXSnackbar
import javafx.application.Platform
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.layout.GridPane
import javafx.stage.Screen
import javafx.stage.Stage
import javafx.stage.StageStyle
import javafx.stage.WindowEvent
import ru.shadowsparky.client.Client.Client
import ru.shadowsparky.client.Utils.ConnectionHandler
import ru.shadowsparky.client.Utils.Injection
import ru.vas7n.va.widgets.MaskField
import java.io.EOFException
import java.net.ConnectException


class Controller : ConnectionHandler  {
    @FXML private lateinit var button: JFXButton
    private val logger = Injection.provideLogger()
    @FXML private lateinit var log: Label
    @FXML private lateinit var addr: MaskField
    @FXML private lateinit var pane: GridPane

    private var stage: Stage? = null

    override fun onSuccess() = Platform.runLater {
        button.isDisable = true
        stage!!.show()
        log.text = "Соединение было успешно установлено"
    }

    override fun onError(e: Exception) = Platform.runLater {
        when (e) {
            is ConnectException -> log.text = "При соединении произошла ошибка.\n Сервер не найден"
            is RuntimeException -> log.text = "Соединение было разорвано.\n Были получены битые данные"
            is EOFException -> log.text = "Произошло отключение от сервера"
            else -> log.text = "Соединение было разорвано.\n Произошла неизвестная ошибка"
        }
        stage?.hide()
        button.isDisable = false
    }

    private fun connect() {
        log.text = "Пытаюсь подключиться..."
        val fxmlLoader = FXMLLoader(javaClass.classLoader.getResource("Video.fxml"))
        val root = fxmlLoader.load<Parent>()
        val controller = fxmlLoader.getController<VideoController>()
        controller.attachClient(Client(controller, this, addr.text))
        controller.start()
        stage = Stage()
        val screen = Screen.getPrimary()
        stage!!.title = "Я хочу сдохнуть"
        stage!!.scene = Scene(root, screen.visualBounds.width, screen.visualBounds.height)
        stage!!.initStyle(StageStyle.UNDECORATED)
        stage!!.isResizable = false
        stage!!.isMaximized = true
        stage!!.scene.window.addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, controller::onDestroy)
    }

    private fun blankAddrHandler() {
        log.text = "Вы обязаны ввести корректный IP адрес"
    }

    @FXML fun initialize() {
        button.setOnAction {
            if ((addr.text.isNotEmpty()) and (!addr.text.contains('_'))) {
                connect()
            } else
                blankAddrHandler()
        }
    }
}