/*
 * Created by shadowsparky in 2019
 */

package ru.shadowsparky.client.Controllers

import javafx.application.Platform
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.stage.Stage
import javafx.stage.WindowEvent
import ru.shadowsparky.client.Client.Client
import ru.shadowsparky.client.Utils.ConnectionHandler
import ru.shadowsparky.client.Utils.Injection
import java.lang.Exception
import java.net.ConnectException

class Controller : ConnectionHandler  {
    @FXML private lateinit var button: Button
    private val log = Injection.provideLogger()
    private var stage: Stage? = null

    override fun onSuccess() = Platform.runLater {
        stage!!.show()
    }

    override fun onError(e: Exception) = Platform.runLater {
        if (e is ConnectException) {
            log.printError("При соединении произошла ошибка. Сервер не найден")
            if (stage != null)
                stage!!.hide()
        }
    }

    @FXML fun initialize() {
        button.setOnAction {
            val fxmlLoader = FXMLLoader(javaClass.classLoader.getResource("Video.fxml"))
            val root = fxmlLoader.load<Parent>()
            val controller = fxmlLoader.getController<VideoController>()
            controller.attachClient(Client(controller, this))
            controller.start()
            stage = Stage()
            stage!!.title = "Я хочу сдохнуть"
            stage!!.scene = Scene(root, 300.0, 275.0)
            stage!!.scene.window.addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, controller::onDestroy)
        }
    }
}