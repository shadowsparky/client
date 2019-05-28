/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.exceptions

import ru.shadowsparky.client.objects.Constants.FORWARD_ERROR

/**
 * Исключение, возникающее когда переопределение порта невозможно
 */
class ForwardException : Exception(FORWARD_ERROR)