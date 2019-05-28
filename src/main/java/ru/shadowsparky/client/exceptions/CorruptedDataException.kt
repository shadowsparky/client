/*
 * Created by shadowsparky in 2019
 */

package ru.shadowsparky.client.exceptions

/**
 * Исключение, возникающее когда данные приходящие с сервера оказываются битыми
 */
class CorruptedDataException(override val message: String?) : Exception (message)