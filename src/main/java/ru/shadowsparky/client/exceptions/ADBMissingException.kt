/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.exceptions

import java.lang.Exception

/**
 * Исключение, возникающее когда ADB не найден
 */
class ADBMissingException : Exception("ADB не найден")