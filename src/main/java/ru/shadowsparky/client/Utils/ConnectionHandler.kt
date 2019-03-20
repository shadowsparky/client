package ru.shadowsparky.client.Utils

import java.lang.Exception

interface ConnectionHandler {
    fun onSuccess()
    fun onError(e: Exception)
}