/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.controllers

import javafx.scene.control.Label
import ru.shadowsparky.client.utils.Injection
import ru.shadowsparky.client.utils.Parser
import ru.shadowsparky.client.utils.adb.ADBDevice
import ru.shadowsparky.client.utils.adb.ADBStatus
import ru.shadowsparky.client.view.AdbView

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
}