/*
 * Created by shadowsparky in 2019
 */

package ru.shadowsparky.client.controllers

import javafx.application.Platform
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.ListView
import javafx.scene.control.ProgressBar
import javafx.scene.layout.StackPane
import ru.shadowsparky.client.utils.*
import ru.shadowsparky.client.utils.adb.ADBStatus
import java.io.EOFException
import java.net.ConnectException

class AdbController : Controllerable, Loadingable, Resultable {
    @FXML private lateinit var addr: ListView<Label>
    @FXML private lateinit var connect: Button
    @FXML private lateinit var loading: ProgressBar
    @FXML private lateinit var stack: StackPane
    private val log = Injection.provideLogger()
    private val launcher = Injection.provideLauncher()
    private lateinit var dialog: Dialog
    private val adb = Injection.provideAdb()
    private var selectedDevice: String? = null

    @FXML fun initialize() {
        setLoading(false)
        dialog = Dialog(stack)
        initDevices()
        connect.setOnAction {
            selectedDevice = addr.selectionModel?.selectedItem?.text
            if (selectedDevice != null) {
                val device = Parser.deviceToStr(selectedDevice!!)
                if (device != null) {
                    val result = adb.forwardPort(device.id)
                    if (result.status == ADBStatus.OK) {
                        launcher.launch(ConnectionType.adb, this)
                        connect.isDisable = true
                    } else {
                        dialog.showDialog("Ошибка", result.info)
                    }
                }
            } else {
                dialog.showDialog("Ошибка", "Вы должны выбрать устройство")
            }
        }
    }

    private fun initDevices() {
        addr.items.clear()
        val result = adb.getDevices()
        if (result.status == ADBStatus.OK) {
            val devices = Parser.strToDevices(result.info)
            devices.forEach {
                addr.items.add(Label(it.toString()))
            }
            if (devices.size == 0) {
                addr.items.add(Label("Нет устройств"))
                addr.isDisable = true
            } else {
                addr.isDisable = false
            }
        } else {
            dialog.showDialog("Ошибка", "Во время чтения подключенных устройств произошла ошибка")
        }
    }

    override fun setLoading(flag: Boolean) = Platform.runLater {
        loading.isVisible = flag
    }

    override fun onSuccess() = Platform.runLater {
        setLoading(false)
        //dialog.showDialog("Очень важная информация", "Соединение успешно установлено")
        launcher.show()
    }

    override fun onError(e: Exception) = Platform.runLater {
        initDevices()
        val error = when (e) {
            is ConnectException -> "При соединении произошла ошибка.\nСервер не найден"
            is RuntimeException -> "Соединение было разорвано.\nБыли получены битые данные"
            is EOFException -> "При подключении к серверу произошла ошибка"
            else -> "Соединение было разорвано.\nПроизошла неизвестная ошибка"
        }
        setLoading(false)
        dialog.showDialog("Ошибка", error)
        log.printInfo("${e.message}")
        connect.isDisable = false
        launcher.hide()
    }
}