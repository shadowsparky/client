/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.mvc.controllers

import ru.shadowsparky.client.mvc.views.BaseView
import ru.shadowsparky.client.mvc.views.WifiView
import ru.shadowsparky.client.utils.exceptions.ProjectionAlreadyStartedException
import ru.shadowsparky.client.utils.interfaces.Controllerable
import ru.shadowsparky.client.utils.projection.ProjectionWorker
import tornadofx.Controller

class WifiController(private val view: WifiView) : Controller(), Controllerable {

    fun startProjection() {
        BaseView.isLoaded.value = false
        if (view.mInputText.get().isNotEmpty()) {
            if ((view.projection == null) or (view.projection?.handling == false)) {
                log.info("ProjectionWorker initializing...")
                view.projection = ProjectionWorker(view, view.mInputText.get())
                view.projection?.start()
            } else {
                view.onError(ProjectionAlreadyStartedException())
            }
        } else {
            view.dialog.showDialog("Ошибка", "Вы должны ввести IP адрес Android устройства")
        }
    }
}