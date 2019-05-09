/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.controllers

import javafx.scene.control.Label
import ru.shadowsparky.client.client.Client
import ru.shadowsparky.client.utils.ConnectionType
import ru.shadowsparky.client.utils.Extras
import ru.shadowsparky.client.utils.Injection
import ru.shadowsparky.client.utils.Parser
import ru.shadowsparky.client.utils.adb.ADBDevice
import ru.shadowsparky.client.utils.adb.ADBStatus
import ru.shadowsparky.client.views.AdbView
import ru.shadowsparky.client.views.CanvasVideoFrame
import ru.shadowsparky.client.views.VideoView

class AdbController(private val view: AdbView) {
    private val adb = Injection.provideAdb()

    fun updateDevices() : ArrayList<ADBDevice>? {
        val request = adb.getDevices()
        if (request.status == ADBStatus.OK) {
            val devices = Parser.strToDevices(request.info)
            view.input.isDisable = if (devices.isNotEmpty()) {
                devices.forEach {
                    view.input.items.add(Label(it.toString()))
                }
                false
            } else {
                view.input.items.add(Label("Нет устройств"))
                true
            }
        }
        return null
    }

    fun showHelp() {
        view.dialog.showDialog(
                "Справка",
                "Для того, чтобы нажимать на экран мобильного устройства, используйте левую кнопку мыши.\n" +
                        "Для возвращения назад нажмите на кнопку Z или B, \n" +
                        "Для открытия меню недавних приложений нажмите на C или R, \n" +
                        "Для нажатия на кнопку 'Домой' нажмите на X или H.",
                true
        )
    }

    fun startProjection() {
        view.video = CanvasVideoFrame("", view, "127.0.0.1", Extras.FORWARD_PORT)
        if (view.deviceAddr != null) {
            val device = Parser.deviceToStr(view.deviceAddr!!)
            if (device != null) {
                adb.forwardPort(device.id)
                view.video?.startProjection()
            }
        } else {
            view.dialog.showDialog("Ошибка", "Вы должны выбрать устройство.")
        }
    }
}