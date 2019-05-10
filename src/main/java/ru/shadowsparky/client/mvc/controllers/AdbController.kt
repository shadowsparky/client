/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.mvc.controllers

import javafx.scene.control.Label
import ru.shadowsparky.client.utils.objects.Constants
import ru.shadowsparky.client.utils.objects.Injection
import ru.shadowsparky.client.utils.objects.Parser
import ru.shadowsparky.client.utils.adb.ADBDevice
import ru.shadowsparky.client.utils.adb.ADBStatus
import ru.shadowsparky.client.mvc.views.AdbView
import ru.shadowsparky.client.mvc.views.VideoView

class AdbController(private val view: AdbView) {


    fun updateDevices() : ArrayList<ADBDevice>? {

//         view.input.isDisable = if (devices.isNotEmpty()) {
//            devices.forEach {
//                view.input.items.add(Label(it.toString()))
//            }
//            false
//        } else {
//            view.input.items.add(Label("Нет устройств"))
//            true
//        }
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
        view.video = VideoView("", view, "127.0.0.1", Constants.FORWARD_PORT)
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