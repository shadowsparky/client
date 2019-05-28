/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.objects

import ru.shadowsparky.client.Logger
import ru.shadowsparky.client.adb.ADBDevice

/**
 * Класс, в котором храняться все методы парсинга объектов
 *
 * @property log подробнее: [Logger]
 */
object Parser {
    private val log = Injection.provideLogger()

    /**
     * Получение списка [ADBDevice] из строки
     */
    fun strToDevices(str: String) : ArrayList<ADBDevice> {
        val result = ArrayList<ADBDevice>()
        val items = str.split("\n")
        items.forEach {
            if (it.length > 30) {
                if (!it.contains("offline", true)) {
                    val model = it
                            .substringAfter("model:")
                            .substringBefore(" device:")
                    val id = it
                            .substringBefore("device")
                            .trim()
                    if ((model.isNotEmpty()) and (id.isNotEmpty())) {
                        result.add(ADBDevice(id, model))
                    }
                    log.printInfo("found device: $model $id")
                }
            }
        }
        return result
    }

    /**
     * Получение [ADBDevice] из строки
     */
    fun strToDevice(str: String) : ADBDevice? {
        val model = str.substringBefore(", id:")
        val id = str.substringAfter(", id:")
        if ((model.isNotEmpty()) && (id.isNotEmpty()))
            return (ADBDevice(id, model))
        return null
    }

}