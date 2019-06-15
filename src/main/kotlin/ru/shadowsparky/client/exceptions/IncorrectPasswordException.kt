/*
 * Created by shadowsparky in 2019
 */

package ru.shadowsparky.client.exceptions

import tornadofx.FX
import tornadofx.get

/**
 * Исключение, возникающее когда был введен неправильный пароль
 */
@Deprecated("Защите паролем не суждено появиться на свет")
class IncorrectPasswordException : Exception(FX.messages["incorrect_password"])