/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.mvvm.models

import ru.shadowsparky.client.Logger
import ru.shadowsparky.client.adb.ADBDevice
import ru.shadowsparky.client.adb.ADBStatus
import ru.shadowsparky.client.adb.ADBWorker
import ru.shadowsparky.client.exceptions.ADBDevicesNotFoundException
import ru.shadowsparky.client.exceptions.ADBMissingException
import ru.shadowsparky.client.exceptions.ForwardException
import ru.shadowsparky.client.exceptions.MissingDeviceException
import ru.shadowsparky.client.objects.Injection
import ru.shadowsparky.client.objects.Parser

/**
 * Model из MVVM для работы с ADB
 *
 * @property adb подробнее: [ADBWorker]
 * @property log подробнее: [Logger]
 */
open class AdbModel {
    private val adb = Injection.provideAdb()
    private val log = Injection.provideLogger()

    /**
     * Отправка запроса на получение устройств
     *
     * @see [ADBWorker.adbDevices], [Parser.strToDevices]
     * @return список [ADBDevice] с подключенными устройствами
     * @exception ADBDevicesNotFoundException срабатывает, если подключенные
     * устройства отсутствуют
     * @exception ADBMissingException срабатывает, если на компьютере
     * пользователя не установлен ADB
     */
    open fun getDevicesRequest() : ArrayList<ADBDevice>  {
        val request = adb.adbDevices()
        if (request.status == ADBStatus.OK) {
            val devices = Parser.strToDevices(request.info)
            if (devices.isEmpty()) throw ADBDevicesNotFoundException()
            return devices
        } else {
            throw ADBMissingException()
        }
    }

    /**
     * Отправка запроса на переопределение порта
     *
     * @param addr данные об устройстве
     * @see [Parser.strToDevice], [ADBWorker.forwardPort]
     * @return всегда true
     * @exception ForwardException срабатыает, если во время переопределения порта произошла ошибка
     */
    fun forwardPort(addr: String) : Boolean {
        val device = Parser.strToDevice(addr)
        if (device != null) {
            val result = adb.forwardPort(device.id)
            if (result.status == ADBStatus.OK)
                return true
        }
        throw ForwardException()
    }

    /**
     * Проверка выбранного устройства
     *
     * @param device данные об устройстве
     * @return данные об устройстве
     * @exception MissingDeviceException срабатывает, если устройство не выбрано
     */
    fun checkDevice(device: String?) = device ?: throw MissingDeviceException()
}