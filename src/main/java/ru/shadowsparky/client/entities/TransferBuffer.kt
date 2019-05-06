/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.entities

data class TransferBuffer(
    val data: ByteArray,
    val flags: Int
)