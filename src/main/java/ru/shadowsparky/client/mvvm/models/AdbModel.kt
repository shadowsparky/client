/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.mvvm.models

import ru.shadowsparky.client.adb.ADBDevice
import ru.shadowsparky.client.adb.ADBStatus
import ru.shadowsparky.client.objects.Injection
import ru.shadowsparky.client.objects.Parser

open class AdbModel {
    private val adb = Injection.provideAdb()

    open fun getDevicesRequest() : ArrayList<ADBDevice>? {
        val request = adb.getDevices()
        if (request.status == ADBStatus.OK) {
            return Parser.strToDevices(request.info)
        }
        return null
    }

    open fun forwardPort(addr: String) : Boolean {
        val device = getDevice(addr)
        if (device != null) {
            val result = adb.forwardPort(device.id)
            if (result.status == ADBStatus.OK)
                return true
        }
        return false
    }

    open fun getDevice(device: String) = Parser.deviceToStr(device)
}