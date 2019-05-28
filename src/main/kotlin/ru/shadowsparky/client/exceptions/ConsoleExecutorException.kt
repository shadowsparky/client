/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.exceptions

/**
 * Исключение, возникающее когда во время выполнения
 * ADB команды происходит ошибка
 */
class ConsoleExecutorException(override val message: String?) : Exception(message)