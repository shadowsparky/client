/*
 * Created by shadowsparky in 2019
 */

package ru.shadowsparky.client.Controllers

import com.jfoenix.controls.JFXButton
import javafx.application.Platform
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.stage.Screen
import javafx.stage.Stage
import javafx.stage.StageStyle
import javafx.stage.WindowEvent
import ru.shadowsparky.client.Client.Client
import ru.shadowsparky.client.Utils.ConnectionHandler
import ru.shadowsparky.client.Utils.Injection
import java.net.ConnectException

class Controller : ConnectionHandler  {
    @FXML private lateinit var button: JFXButton
    private val log = Injection.provideLogger()
    private var stage: Stage? = null

    override fun onSuccess() = Platform.runLater {
        button.isDisable = true
        stage!!.show()
    }

    override fun onError(e: Exception) = Platform.runLater {
        if (e is ConnectException) {
            log.printError("При соединении произошла ошибка. Сервер не найден")
        } else if (e is RuntimeException) {
            log.printError("Соединение было разорвано. Были получены битые данные")
        }
        if (stage != null)
            stage!!.hide()
        button.isDisable = false
    }

    @FXML fun initialize() {
        button.setOnAction {
            val fxmlLoader = FXMLLoader(javaClass.classLoader.getResource("Video.fxml"))
            val root = fxmlLoader.load<Parent>()
            val controller = fxmlLoader.getController<VideoController>()
            controller.attachClient(Client(controller, this))
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
    }
}