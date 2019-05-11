/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.projection

import java.io.File
import java.io.FileOutputStream

class VideoWriter(path: String) {
    private val stream = FileOutputStream(File(path))
    fun writeToFile(data: ByteArray) = stream.write(data)
    fun close() = stream.close()
}