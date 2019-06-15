/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.exceptions

import tornadofx.FX
import tornadofx.get

/**
 * Исключение, возникающее когда устройства подключенные по ADB не найдены
 */
class ADBDevicesNotFoundException : Exception(FX.messages["no_connected_devices"])