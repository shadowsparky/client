/*
 * Created by shadowsparky in 2019
 */

package ru.shadowsparky.client.exceptions

/**
 * Исключение, возникающее когда был введен неправильный пароль
 */
@Deprecated("Защите паролем не суждено появиться на свет")
class IncorrectPasswordException : Exception("Был введен неправильный пароль")