package ru.shadowsparky.client.utils.adb

data class ADBDevice(
    val id: String,
    val model: String
) {
    override fun toString(): String {
        return "$model, id:$id"
    }
}