/*
 * Created by shadowsparky in 2019
 *
 */

/*
 * Created by shadowsparky in 2019
 */

package ru.shadowsparky.client

import org.apache.commons.io.IOUtils
import ru.shadowsparky.client.exceptions.ConsoleExecutorException
import java.io.IOException
import java.nio.charset.Charset

/**
 * Класс, с помощью которого можно выполнять консольные команды и возвращать результат
 */
class ConsoleExecutor {

    /**
     * Выполнение команды
     *
     * @param commands список, из которого строится команда
     * @return результат команды
     */
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