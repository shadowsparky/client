/*
 * Created by shadowsparky in 2019
 */

package ru.shadowsparky.client

import java.util.logging.Level
import java.util.logging.Logger

/**
 * Обертка для логгирования
 *
 * @property loggy логгер
 */
class Logger {
    private val loggy = Logger.getLogger("Logger")

    /**
     * Вывести информацию
     */
    fun printInfo(message: String) = loggy.info(message)

    /**
     * Вывести ошибку
     */
    fun printError(message: String) = loggy.log(Level.SEVERE, message)
}