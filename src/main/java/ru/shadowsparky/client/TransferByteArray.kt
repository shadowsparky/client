/*
 * Created by shadowsparky in 2019
 */

package ru.shadowsparky.screencast

import java.io.Serializable

class TransferByteArray(
        val data: ByteArray,
        val length: Int
) : Serializable