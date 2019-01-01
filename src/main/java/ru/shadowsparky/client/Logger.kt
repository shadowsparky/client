package ru.shadowsparky.client

import java.util.logging.Level

class Logger {
    private val loggy = java.util.logging.Logger.getLogger("Logger")

    fun printInfo(message: String) = loggy.info(message)
    fun printWarning(message: String) = loggy.warning(message)
    fun printError(message: String) = loggy.log(Level.SEVERE, message)
    fun printFine(message: String) = loggy.log(Level.FINE, message)
}