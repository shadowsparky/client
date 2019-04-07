package ru.shadowsparky.client.Utils

import ru.shadowsparky.client.Utils.ADB.ADBDevice

object Parser {
    private val log = Injection.provideLogger()

    fun strToDevices(str: String) : ArrayList<ADBDevice> {
        val result = ArrayList<ADBDevice>()
        val items = str.split("\n")
        items.forEach {
            if (it.length > 30) {
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
        return result
    }

    fun deviceToStr(str: String) : ADBDevice? {
        val model = str.substringBefore(", id:")
        val id = str.substringAfter(", id:")
        if ((model.isNotEmpty()) && (id.isNotEmpty()))
            return (ADBDevice(id, model))
        return null
    }

}