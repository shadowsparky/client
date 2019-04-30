/*
 * Created by shadowsparky in 2019
 */

package ru.shadowsparky.client.utils

import org.apache.commons.io.IOUtils
import ru.shadowsparky.client.utils.exceptions.ConsoleExecutorException
import java.io.IOException
import java.nio.charset.Charset

class ConsoleExecutor {
    fun executeCommand(commands: List<String>) : String {
        return try {
            val proc = ProcessBuilder()
                    .command(commands)
                    .start()
            IOUtils.toString(proc.inputStream, Charset.defaultCharset())
        } catch(e: IOException) {
            throw ConsoleExecutorException(e.message)
        }
    }
}