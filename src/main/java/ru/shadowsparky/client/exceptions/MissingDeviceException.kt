/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.exceptions

import ru.shadowsparky.client.objects.Constants
import java.lang.Exception

/**
 * Исключение, возникающее когда пользователь не выбрал устройство
 */
class MissingDeviceException : Exception(Constants.CHOOSE_DEVICE_ERROR)