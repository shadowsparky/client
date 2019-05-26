/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.mvvm.models

import ru.shadowsparky.client.adb.ADBDevice
import ru.shadowsparky.client.adb.ADBStatus
import ru.shadowsparky.client.exceptions.ADBDevicesNotFoundException
import ru.shadowsparky.client.exceptions.ADBMissingException
import ru.shadowsparky.client.exceptions.ForwardException
import ru.shadowsparky.client.exceptions.MissingDeviceException
import ru.shadowsparky.client.objects.Injection
import ru.shadowsparky.client.objects.Parser

open class AdbModel {
    private val adb = Injection.provideAdb()
    private val log = Injection.provideLogger()

    open fun getDevicesRequest() : ArrayList<ADBDevice> {
        val request = adb.getDevices()
        if (request.status == ADBStatus.OK) {
            val devices = Parser.strToDevices(request.info)
            if (devices.isEmpty()) throw ADBDevicesNotFoundException()
            return devices
        } else {
            throw ADBMissingException()
        }
    }

    open fun forwardPort(addr: String) : Boolean {
        val device = getDevice(addr)
        if (device != null) {
            val result = adb.forwardPort(device.id)
            if (result.status == ADBStatus.OK)
                return true
        }
        throw ForwardException()
    }

    open fun checkDevice(device: String?) = device ?: throw MissingDeviceException()

    open fun getDevice(device: String) = Parser.deviceToStr(device)
}