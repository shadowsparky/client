/*
 * Created by shadowsparky in 2019
 */

package ru.shadowsparky.client.utils

import org.apache.commons.io.IOUtils
import java.nio.charset.Charset

class ConsoleExecutor {
    fun executeCommand(commands: List<String>) : String {
        val proc = ProcessBuilder()
                .command(commands)
                .start()
        return IOUtils.toString(proc.inputStream, Charset.defaultCharset())
    }
}