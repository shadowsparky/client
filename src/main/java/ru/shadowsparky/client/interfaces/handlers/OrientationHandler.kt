/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.interfaces.handlers

/**
 * Класс, имплементирующий этот интерфейс оповещается о смене ориентации Android устройства
 */
interface OrientationHandler {
    /**
     * Срабатывает при смене ориентации Android устройства
     *
     * @param newHeight новая высота Android устройства
     * @param newWidth новая ширина Android устройства
     */
    fun onOrientationChanged(newWidth: Int, newHeight: Int)
}