/*
 * Created by shadowsparky in 2019
 */

package ru.shadowsparky.client.Controllers

import javafx.fxml.FXML
import javafx.scene.control.TabPane
import javafx.scene.layout.BorderPane
import ru.shadowsparky.client.Utils.Controllerable
import ru.shadowsparky.client.Utils.Injection
import ru.shadowsparky.client.Utils.Injector

enum class ConnectionType {
    adb, wifi
}

class MainController : Controllerable {
    @FXML private lateinit var root: BorderPane
    @FXML private lateinit var tabPane: TabPane
    private val log = Injection.provideLogger()
    private var injector: Injector? = null

    @FXML fun initialize() {
        injector = Injection.provideInjector(root)
        injector?.injectScreen("wifi_layout.fxml", WifiController())
        initTab()
    }

    private fun initTab() {
        tabPane.selectionModel.selectedItemProperty().addListener { obs, ov, nv ->
            when(nv.text) {
                "WIFI" -> {
                    log.printInfo("Wifi Clicked")
                    injector?.injectScreen("wifi_layout.fxml", WifiController())
                }
                "ADB" -> {
                    log.printInfo("ADB Clicked")
                    injector?.injectScreen("adb_layout.fxml", AdbController())
                }
                "Settings" -> { log.printInfo("Settings clicked") }
            }
        }
    }
}

//class Controller : ConnectionHandler  {

//    @FXML private lateinit var connButton: JFXButton
//    private val logger = Injection.provideLogger()
//    private val adb = Injection.provideAdb()
//    @FXML private lateinit var log: Label
//    @FXML private lateinit var addr: JFXTextField
//    @FXML private lateinit var pane: GridPane
//    @FXML private lateinit var adbConn: JFXButton
//    @FXML private lateinit var adbDevices: JFXListView<Label>
//    @FXML private lateinit var adbTab: Tab
//    @FXML private lateinit var tabPane: JFXTabPane
//    private var selectedDevice: String? = null
//
//
//    private var stage: Stage? = null
//
//    override fun onSuccess() = Platform.runLater {
//        connButton.isDisable = true
//        adbConn.isDisable = true
//        stage!!.show()
//        log.text = "Соединение было успешно установлено"
//    }
//
//    override fun onError(e: Exception) = Platform.runLater {
//        when (e) {
//            is ConnectException -> log.text = "При соединении произошла ошибка.\n Сервер не найден"
//            is RuntimeException -> log.text = "Соединение было разорвано.\n Были получены битые данные"
//            is EOFException -> log.text = "Произошло отключение от сервера"
//            else -> log.text = "Соединение было разорвано.\n Произошла неизвестная ошибка"
//        }
//        stage?.hide()
//        connButton.isDisable = false
//        adbConn.isDisable = false
//    }
//
//
//    private fun blankAddrHandler() {
//        log.text = "Вы обязаны ввести корректный IP адрес"
//    }
//
//    @FXML fun initialize() {
//        adbTab.selectedProperty().addListener { _ ->
//            adbDevices.items.clear()
//            logger.printInfo("Devices updated")
//            ADBTest.executeCommand(listOf("adb", "devices", "-l"))
//            val out = ADBTest.getOutput()
//            val devices = ADBTest.parseDevices(out)
//            devices.forEach {
//                adbDevices.items.add(Label(it.toString()))
//            }
//            if (devices.size == 0) {
//                adbDevices.items.add(Label("Нет устройств"))
//                adbDevices.isDisable = true
//            } else {
//                adbDevices.isDisable = false
//            }
//        }
//        connButton.setOnAction {
//            if (addr.text.isNotEmpty()) {
//                connect(ConnectionType.wifi)
//            } else
//                blankAddrHandler()
//        }
//
//        adbConn.setOnAction {
//            selectedDevice = adbDevices.selectionModel?.selectedItem?.text
//            if (selectedDevice != null) {
//                log.text = ""
//                val device = ADBDevice.parseDevice(selectedDevice!!)
//                if (device != null) {
//                    ADBTest.executeCommand(listOf("adb", "-s", device.id, "forward", "tcp:${Extras.FORWARD_PORT}", "tcp:${Extras.PORT}"))
//                    if (ADBTest.getOutput().isEmpty()) {
//                        connect(ConnectionType.adb)
//                    }
//                }
//            } else {
//                log.text = "Вы должны выбрать устройство"
//            }
//        }
//    }
//}