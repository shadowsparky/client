/*
 * Created by shadowsparky in 2019
 */

package ru.shadowsparky.client.adb

/**
 * Модель, содержащая информацию об устройстве
 *
 * @param id идентификатор устройства (локальный ipv4 адрес)
 * @param model название устройства
 */
data class ADBDevice (
    val id: String,
    val model: String
) {
    /**
     * Конвертирование модели в строку
     *
     * @return информация о модели в виде строки
     */
    override fun toString(): String {
        return "$model, id:$id"
    }
}
