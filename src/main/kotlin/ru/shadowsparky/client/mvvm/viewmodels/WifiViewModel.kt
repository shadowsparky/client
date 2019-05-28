/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.mvvm.viewmodels

import javafx.beans.property.SimpleStringProperty
import ru.shadowsparky.client.mvvm.models.WifiModel
import ru.shadowsparky.client.mvvm.views.BaseView
import ru.shadowsparky.client.mvvm.views.WifiView
import ru.shadowsparky.client.objects.Injection
import tornadofx.ViewModel

/**
 * ViewModel из MVVM для работы с wifi
 *
 * @param view подробнее: [WifiView]
 * @param model подробнее: [WifiModel]
 * @property mDeviceAddr проперти, связанное с EditText, в котором вводится адрес устройства (binding)
 */
class WifiViewModel(
        private val view: WifiView,
        private val model: WifiModel = WifiModel()
) : ViewModel() {
    val mDeviceAddr = SimpleStringProperty("192.168.31.221")

    /**
     * Запуск проецирования
     */
    fun startProjection() {
        BaseView.isLoaded.value = false
        BaseView.isLocked.value = true
        try {
            model.checkIpAddress(mDeviceAddr.get())
            model.checkProjection(view.projection)
            view.projection = Injection.provideProjectionWorker(view, mDeviceAddr.get())
            view.projection!!.start()
        } catch (e: Exception) {
            view.onError(e)
        }
    }
}