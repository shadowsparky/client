/*
 * Created by shadowsparky in 2019
 */

package ru.shadowsparky.client.adb

data class ADBResult (
        val status: ADBStatus,
        val info: String = ""
)