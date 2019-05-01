/*
 * Created by shadowsparky in 2019
 */

package ru.shadowsparky.client.utils.exceptions

import java.lang.Exception

class CorruptedDataException(override val message: String?) : Exception (message)