/*
 * Created by shadowsparky in 2019
 */

package ru.shadowsparky.client.utils.exceptions

import java.lang.Exception

class CorruptedDataExcetion(override val message: String?) : Exception (message)