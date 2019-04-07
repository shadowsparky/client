package ru.shadowsparky.client.Utils.ADB

data class ADBResult (
    val status: ADBStatus,
    val info: String = ""
)