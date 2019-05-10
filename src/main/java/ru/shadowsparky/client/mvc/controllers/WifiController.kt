/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.mvc.controllers

import ru.shadowsparky.client.utils.objects.Injection
import ru.shadowsparky.client.utils.exceptions.ProjectionAlreadyStartedException
import ru.shadowsparky.client.mvc.views.VideoView
import ru.shadowsparky.client.mvc.views.WifiView
import tornadofx.Controller

class WifiController(private val view: WifiView) : Controller() {
    private val _log = Injection.provideLogger()

    fun startProjection() {
        if (view.mInputText.get().isNotEmpty()) {
            if ((view.video == null) or (view.video?.client?.handling == false)) {
                view.video = VideoView("Проецирование", view, view.mInputText.get())
                view.video!!.startProjection()
            } else {
                view.onError(ProjectionAlreadyStartedException())
            }
        } else {
            view.dialog.showDialog("Ошибка", "Вы должны ввести адрес Android устройства")
        }
    }
}