/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.mvc.models

import ru.shadowsparky.client.adb.ADBDevice
import ru.shadowsparky.client.adb.ADBStatus
import ru.shadowsparky.client.objects.Injection
import ru.shadowsparky.client.objects.Parser

class AdbModel {
    private val adb = Injection.provideAdb()

    fun getDevicesRequest() : ArrayList<ADBDevice>? {
        val request = adb.getDevices()
        if (request.status == ADBStatus.OK) {
            return Parser.strToDevices(request.info)
        }
        return null
    }

    fun forwardPort(addr: String) : Boolean {
        val device = getDevice(addr)
        if (device != null) {
            val result = adb.forwardPort(device.id)
            if (result.status == ADBStatus.OK)
                return true
        }
        return false
    }

    fun getDevice(device: String) = Parser.deviceToStr(device)
}