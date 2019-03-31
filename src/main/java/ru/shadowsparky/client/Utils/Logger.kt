/*
 * Created by shadowsparky in 2019
 */

package ru.shadowsparky.client.Utils

import java.util.logging.Level
import java.util.logging.Logger

class Logger {
    private val loggy = Logger.getLogger("Logger")

    fun printInfo(message: String) = loggy.info(message)
    fun printWarning(message: String) = loggy.warning(message)
    fun printError(message: String) = loggy.log(Level.SEVERE, message)
    fun printFine(message: String) = loggy.log(Level.FINE, message)
}