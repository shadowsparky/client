/*
 * Created by shadowsparky in 2019
 */

package ru.shadowsparky.client.Utils

import java.lang.Exception

interface Resultable {
    fun onSuccess()
    fun onError(e: Exception)
}