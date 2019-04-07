package ru.shadowsparky.client.Utils.ADB

data class ADBDevice(
    val id: String,
    val model: String
) {
    override fun toString(): String {
        return "$model, id:$id"
    }
}