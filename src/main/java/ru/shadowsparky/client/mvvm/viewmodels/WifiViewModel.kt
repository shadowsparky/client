/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.mvvm.viewmodels

import javafx.beans.property.SimpleStringProperty
import ru.shadowsparky.client.mvvm.views.BaseView
import ru.shadowsparky.client.mvvm.views.WifiView
import ru.shadowsparky.client.exceptions.ProjectionAlreadyStartedException
import ru.shadowsparky.client.interfaces.Controllerable
import ru.shadowsparky.client.mvvm.models.WifiModel
import ru.shadowsparky.client.objects.Constants
import ru.shadowsparky.client.objects.Constants.ERROR
import ru.shadowsparky.client.objects.Constants.INCORRECT_IP
import ru.shadowsparky.client.objects.Injection
import ru.shadowsparky.client.projection.ProjectionWorker
import tornadofx.Controller

class WifiViewModel(
        private val view: WifiView,
        private val model: WifiModel = WifiModel()
) : Controller(), Controllerable {
    val mDeviceAddr = SimpleStringProperty("192.168.31.221")

    fun startProjection() {
        BaseView.isLoaded.value = false
        if (mDeviceAddr.get().isNotEmpty()) {
            if ((view.projection == null) or (view.projection?.handling == false)) {
                // Понятия не имею как это проверять.
                log.info("ProjectionWorker initializing...")
//                view.projection = ProjectionWorker(view, view.mInputText.get())
                view.projection = Injection.provideProjectionWorker(view, mDeviceAddr.get())
                view.projection!!.start()
            } else {
                view.onError(ProjectionAlreadyStartedException())
            }
        } else {
            view.dialog.showDialog(ERROR, INCORRECT_IP)
        }
    }
}