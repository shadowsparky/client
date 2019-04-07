/*
 * Created by shadowsparky in 2019
 */

package ru.shadowsparky.client.utils.adb

data class ADBResult (
    val status: ADBStatus,
    val info: String = ""
)