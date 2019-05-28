/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.exceptions

import ru.shadowsparky.client.objects.Constants.INCORRECT_IP

/**
 * Исключение, возникающее когда пользователь вводит некорректный IP адрес
 */
class EmptyAddressException : Exception(INCORRECT_IP)