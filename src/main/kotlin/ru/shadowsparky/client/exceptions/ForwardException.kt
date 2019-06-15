/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.exceptions

import tornadofx.FX
import tornadofx.get

/**
 * Исключение, возникающее когда переопределение порта невозможно
 */
class ForwardException : Exception(FX.messages["forward_error"])