/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.exceptions

import ru.shadowsparky.client.objects.Constants
import tornadofx.FX
import tornadofx.get
import java.lang.Exception

/**
 * Исключение, возникающее когда пользователь не выбрал устройство
 */
class MissingDeviceException : Exception(FX.messages["choose_device_error"])