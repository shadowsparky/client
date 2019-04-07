package ru.shadowsparky.client.utils.adb

data class ADBResult (
    val status: ADBStatus,
    val info: String = ""
)