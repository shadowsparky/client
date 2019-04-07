package ru.shadowsparky.client.Utils

import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Screen
import javafx.stage.Stage
import javafx.stage.StageStyle
import javafx.stage.WindowEvent
import ru.shadowsparky.client.Client.Client
import ru.shadowsparky.client.Controllers.ConnectionType
import ru.shadowsparky.client.Controllers.VideoController

class Launcher() {
    private var stage: Stage? = null

    fun launch(type: ConnectionType, handler: ConnectionHandler, addr: String? = null) {
        val fxmlLoader = FXMLLoader(javaClass.classLoader.getResource(LayoutConsts.VIDEO_FXML))
        val root = fxmlLoader.load<Parent>()
        val controller = fxmlLoader.getController<VideoController>()
        if (type == ConnectionType.adb) {
            controller.attachClient(Client(controller, handler, "127.0.0.1", Extras.FORWARD_PORT))
        } else if (type == ConnectionType.wifi)
            controller.attachClient(Client(controller, handler, addr!!))
        controller.start()
        stage = Stage()
        val screen = Screen.getPrimary()
        stage?.title = "Screencast"
        stage?.scene = Scene(root)
        stage?.initStyle(StageStyle.UNDECORATED)
        stage?.isResizable = false
        stage?.isMaximized = true
        stage?.scene?.window?.addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, controller::onDestroy)
        controller.attachStage(stage)
    }

    fun show() {
        stage?.showAndWait()
    }

    fun hide() {
        stage?.hide()
    }
}