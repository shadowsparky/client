/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.mvc.controllers

import ru.shadowsparky.client.mvc.views.AdbView
import ru.shadowsparky.client.mvc.views.BaseView
import ru.shadowsparky.client.interfaces.Controllerable
import ru.shadowsparky.client.mvc.models.AdbModel
import ru.shadowsparky.client.objects.Constants.FORWARD_PORT
import ru.shadowsparky.client.objects.Constants.LOCALHOST
import ru.shadowsparky.client.objects.Injection
import ru.shadowsparky.client.projection.ProjectionWorker
import tornadofx.Controller

class AdbController(
        private val view: AdbView,
        private val model: AdbModel = Injection.provideAdbModel()
) : Controller(), Controllerable {
    private val _log = Injection.provideLogger()

    fun updateDevices() {
        view.clearDevices()
        val devices = model.getDevicesRequest()
        if (devices != null) {
            val result = if (devices.isNotEmpty()) {
                devices.forEach {
                    view.addDevice("$it")
                }
                false
            } else {
                view.addDevice("Нет подключенных устройств")
                true
            }
            view.setDisable(result)
        }
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
        BaseView.isLoaded.value = false
        if (view.deviceAddr != null) {
            if (model.forwardPort(view.deviceAddr!!)) {
                view.projection = ProjectionWorker(view, LOCALHOST, FORWARD_PORT)
                view.projection?.start()
            } else {
                view.dialog.showDialog("Ошибка", "Во время открытия порта произошла неизвестная ошибка.")
            }
        } else {
            view.dialog.showDialog("Ошибка", "Вы должны выбрать устройство.")
        }
    }
}