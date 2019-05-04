/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.controllers

import ru.shadowsparky.client.client.Client
import ru.shadowsparky.client.utils.ConnectionType
import ru.shadowsparky.client.utils.Injection
import ru.shadowsparky.client.utils.exceptions.ProjectionAlreadyStartedException
import ru.shadowsparky.client.utils.exceptions.StartProjectionException
import ru.shadowsparky.client.views.VideoView
import ru.shadowsparky.client.views.WifiView
import tornadofx.Controller

class WifiController(private val view: WifiView) : Controller() {
    private val _log = Injection.provideLogger()

    fun startProjection() {
        if (view.mInputText.get().isNotEmpty()) {
            if ((view.video == null) or (view.video?.client?.handling == false)) {
                view.video = VideoView(ConnectionType.wifi)
                view.video!!.client = Client(view.video!!, view, view.mInputText.get())
                view.video!!.client?.start()
            } else {
                view.onError(ProjectionAlreadyStartedException())
            }
        } else {
            view.dialog.showDialog("Ошибка", "Вы должны ввести адрес Android устройства")
        }
    }
}