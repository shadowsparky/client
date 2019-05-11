/*
 * Created by shadowsparky in 2019
 */

package ru.shadowsparky.client.adb

data class ADBDevice(
    val id: String,
    val model: String
) {
    override fun toString(): String {
        return "$model, id:$id"
    }
}