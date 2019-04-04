/*
 * Created by shadowsparky in 2019
 */

package ru.shadowsparky.client.Controllers

import com.jfoenix.controls.JFXButton
import com.jfoenix.controls.JFXListView
import com.jfoenix.controls.JFXTabPane
import com.jfoenix.controls.JFXTextField
import javafx.application.Platform
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.control.Tab
import javafx.scene.layout.GridPane
import javafx.stage.Screen
import javafx.stage.Stage
import javafx.stage.StageStyle
import javafx.stage.WindowEvent
import ru.shadowsparky.client.Client.Client
import ru.shadowsparky.client.Utils.*
import se.vidstige.jadb.JadbConnection
import java.io.EOFException
import java.lang.UnsupportedOperationException
import java.net.ConnectException

enum class ConnectionType {
    adb, wifi
}

class Controller : ConnectionHandler  {
    @FXML private lateinit var connButton: JFXButton
    private val logger = Injection.provideLogger()
    private val adb = Injection.provideAdb()
    @FXML private lateinit var log: Label
    @FXML private lateinit var addr: JFXTextField
    @FXML private lateinit var pane: GridPane
    @FXML private lateinit var adbConn: JFXButton
    @FXML private lateinit var adbDevices: JFXListView<Label>
    @FXML private lateinit var adbTab: Tab
    @FXML private lateinit var tabPane: JFXTabPane
    private var selectedDevice: String? = null


    private var stage: Stage? = null

    override fun onSuccess() = Platform.runLater {
        connButton.isDisable = true
        adbConn.isDisable = true
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
        connButton.isDisable = false
        adbConn.isDisable = false
    }

    private fun connect(type: ConnectionType) {
        log.text = "Пытаюсь подключиться..."
        val fxmlLoader = FXMLLoader(javaClass.classLoader.getResource("Video.fxml"))
        val root = fxmlLoader.load<Parent>()
        val controller = fxmlLoader.getController<VideoController>()
        if (type == ConnectionType.adb) {
            controller.attachClient(Client(controller, this, "127.0.0.1", Extras.FORWARD_PORT))
        } else if (type == ConnectionType.wifi)
            controller.attachClient(Client(controller, this, addr.text))
        controller.start()
        stage = Stage()
        val screen = Screen.getPrimary()
        stage!!.title = "Я хочу сдохнуть"
        stage!!.scene = Scene(root, screen.visualBounds.width, screen.visualBounds.height)
//        stage!!.initStyle(StageStyle.UNDECORATED)
//        stage!!.isResizable = false
//        stage!!.isMaximized = true
        stage!!.scene.window.addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, controller::onDestroy)
        controller.attachStage(stage)
    }

    private fun blankAddrHandler() {
        log.text = "Вы обязаны ввести корректный IP адрес"
    }

    @FXML fun initialize() {
        adbTab.selectedProperty().addListener { _ ->
            adbDevices.items.clear()
            logger.printInfo("Devices updated")
            ADBTest.executeCommand(listOf("adb", "devices", "-l"))
            val out = ADBTest.getOutput()
            val devices = ADBTest.parseDevices(out)
            devices.forEach {
                adbDevices.items.add(Label(it.toString()))
            }
            if (devices.size == 0) {
                adbDevices.items.add(Label("Нет устройств"))
                adbDevices.isDisable = true
            } else {
                adbDevices.isDisable = false
            }
        }
        connButton.setOnAction {
            if (addr.text.isNotEmpty()) {
                connect(ConnectionType.wifi)
            } else
                blankAddrHandler()
        }

        adbConn.setOnAction {
            selectedDevice = adbDevices.selectionModel?.selectedItem?.text
            if (selectedDevice != null) {
                log.text = ""
                val device = ADBDevice.parseDevice(selectedDevice!!)
                if (device != null) {
                    ADBTest.executeCommand(listOf("adb", "-s", device.id, "forward", "tcp:${Extras.FORWARD_PORT}", "tcp:${Extras.PORT}"))
                    if (ADBTest.getOutput().isEmpty()) {
                        connect(ConnectionType.adb)
                    }
                }
            } else {
                log.text = "Вы должны выбрать устройство"
            }
        }
    }
}