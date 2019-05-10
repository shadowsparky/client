/*
 * Created by shadowsparky in 2019
 */

package ru.shadowsparky.client.utils.interfaces

import java.lang.Exception

interface Resultable {
    fun onSuccess()
    fun onError(e: Exception)
}