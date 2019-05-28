/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.exceptions

/**
 * Исключение, возникающее когда устройства подключенные по ADB не найдены
 */
class ADBDevicesNotFoundException : Exception("Нет подключенных устройств")