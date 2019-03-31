package ru.shadowsparky.client.Utils

import org.apache.commons.io.IOUtils
import java.nio.charset.Charset

object ADBTest {
    private val log = Injection.provideLogger()
    private var proc: Process? = null

    fun getOutput() : String {
        return IOUtils.toString(proc?.inputStream, Charset.defaultCharset())
    }

    fun executeCommand(commands: List<String>) {
        proc = ProcessBuilder()
                .command(commands)
                .start()
    }
}