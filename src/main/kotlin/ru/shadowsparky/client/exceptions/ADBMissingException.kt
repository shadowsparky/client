/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.exceptions

import tornadofx.FX
import tornadofx.get
import java.lang.Exception

/**
 * Исключение, возникающее когда ADB не найден
 */
class ADBMissingException : Exception(FX.messages["adb_not_installed"])