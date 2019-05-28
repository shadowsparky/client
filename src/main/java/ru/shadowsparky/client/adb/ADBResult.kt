/*
 * Created by shadowsparky in 2019
 */

package ru.shadowsparky.client.adb

/**
 * Модель, содерРезультат выполнения ADB
 *
 * @param status статус выполнения. Подробнее: [ADBStatus]
 * @param info информация о выполнении
 */
data class ADBResult (
    val status: ADBStatus,
    val info: String = ""
)