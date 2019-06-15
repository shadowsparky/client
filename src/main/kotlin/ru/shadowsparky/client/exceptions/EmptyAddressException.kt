/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.exceptions

import tornadofx.FX
import tornadofx.get

/**
 * Исключение, возникающее когда пользователь вводит некорректный IP адрес
 */
class EmptyAddressException : Exception(FX.messages["server_not_found"])