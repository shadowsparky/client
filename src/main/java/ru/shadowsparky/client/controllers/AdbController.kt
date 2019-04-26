/*
 * Created by shadowsparky in 2019
 */

package ru.shadowsparky.client.controllers

import javafx.application.Platform
import javafx.fxml.FXML
import javafx.scene.control.Label
import javafx.scene.control.ListView
import ru.shadowsparky.client.utils.Injection
import ru.shadowsparky.client.utils.Parser
import ru.shadowsparky.client.utils.adb.ADBStatus

class AdbController : BaseConnectionFragment() {
    private val adb = Injection.provideAdb()
    private var selectedDevice: String? = null
    @FXML protected lateinit var addr: ListView<Label>

    @FXML fun initialize() {
        this.init()
        initDevices()
        connect.setOnAction {
            if (connect.text == "Подключиться") {
                selectedDevice = addr.selectionModel?.selectedItem?.text
                if (selectedDevice != null) {
                    val device = Parser.deviceToStr(selectedDevice!!)
                    if (device != null) {
                        val result = adb.forwardPort(device.id)
                        if (result.status == ADBStatus.OK) {
                            launcher.launch(ConnectionType.adb, this)
                            connect.text = "Отключиться"
                        } else {
                            dialog.showDialog("Ошибка", result.info)
                        }
                    }
                } else {
                    dialog.showDialog("Ошибка", "Вы должны выбрать устройство")
                }
            } else {
                launcher.hide()
                connect.text = "Подключиться"
            }
        }
    }

    override fun onError(e: Exception) {
        super.onError(e)
        initDevices()
    }

    private fun initDevices() = Platform.runLater {
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
}