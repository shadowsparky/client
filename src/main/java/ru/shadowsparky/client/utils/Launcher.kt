/*
 * Created by shadowsparky in 2019
 */

package ru.shadowsparky.client.utils

import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Screen
import javafx.stage.Stage
import javafx.stage.StageStyle
import javafx.stage.WindowEvent
import org.scenicview.ScenicView
import ru.shadowsparky.client.client.Client
import ru.shadowsparky.client.controllers.ConnectionType
import ru.shadowsparky.client.controllers.VideoController

class Launcher() {
    private var stage: Stage? = null
    private lateinit var controller: VideoController

    fun launch(type: ConnectionType, handler: Resultable, addr: String? = null) {
        val fxmlLoader = FXMLLoader(javaClass.classLoader.getResource(LayoutConsts.VIDEO_FXML))
        val root = fxmlLoader.load<Parent>()
        controller = fxmlLoader.getController<VideoController>()
        if (type == ConnectionType.adb) {
            controller.attachClient(Client(controller, handler, "127.0.0.1", Extras.FORWARD_PORT))
        } else if (type == ConnectionType.wifi)
            controller.attachClient(Client(controller, handler, addr!!))
        controller.start()
        stage = Stage()
        //Screen.getPrimary()
        stage?.title = "Screencast"
        stage?.scene = Scene(root)
        stage?.initStyle(StageStyle.UNDECORATED)
        stage?.isResizable = false
        stage?.isMaximized = true
        stage?.scene?.window?.addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, controller::onDestroy)
        controller.attachStage(stage)
//        ScenicView.show(stage?.scene)
    }

    fun show() {
        stage?.showAndWait()
    }

    fun hide() {
        controller.stop()
        stage?.hide()
    }
}