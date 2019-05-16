/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.mvvm.viewmodels

import ru.shadowsparky.client.mvvm.views.AdbView
import ru.shadowsparky.client.mvvm.views.BaseView
import ru.shadowsparky.client.interfaces.Controllerable
import ru.shadowsparky.client.mvvm.models.AdbModel
import ru.shadowsparky.client.objects.Constants.ADB_NOT_FOUND
import ru.shadowsparky.client.objects.Constants.CHOOSE_DEVICE_ERROR
import ru.shadowsparky.client.objects.Constants.ERROR
import ru.shadowsparky.client.objects.Constants.FAQ
import ru.shadowsparky.client.objects.Constants.FAQ_MESSAGE
import ru.shadowsparky.client.objects.Constants.FORWARD_ERROR
import ru.shadowsparky.client.objects.Constants.FORWARD_PORT
import ru.shadowsparky.client.objects.Constants.LOCALHOST
import ru.shadowsparky.client.objects.Constants.NO_CONNECTED_DEVICES
import ru.shadowsparky.client.objects.Injection
import ru.shadowsparky.client.projection.ProjectionWorker
import tornadofx.Controller

open class AdbViewModel(
        private val view: AdbView,
        private val model: AdbModel = Injection.provideAdbModel()
) : Controller(), Controllerable {
    private val _log = Injection.provideLogger()

    fun updateDevices() {
        view.clearDevices()
        var result = true
        val devices = model.getDevicesRequest()
        if (devices != null) {
            result = if (devices.isNotEmpty()) {
                devices.forEach {
                    view.addDevice("$it")
                }
                false
            } else {
                view.addDevice(NO_CONNECTED_DEVICES)
                true
            }
        } else {
            view.addDevice(ADB_NOT_FOUND)
        }
        view.setDisable(result)
    }

    fun showHelp() = view.dialog.showDialog(FAQ, FAQ_MESSAGE, true)

    open fun startProjection() {
        BaseView.isLoaded.value = false
        if (view.deviceAddr != null) {
            if (model.forwardPort(view.deviceAddr!!)) {
                view.projection = ProjectionWorker(view, LOCALHOST, FORWARD_PORT)
                view.projection?.start()
            } else {
                view.dialog.showDialog(ERROR, FORWARD_ERROR, true)
            }
        } else {
            view.dialog.showDialog(ERROR, CHOOSE_DEVICE_ERROR)
        }
    }
}