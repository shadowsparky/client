@file:Suppress("UNREACHABLE_CODE")

package ru.shadowsparky.client.utils.adb

import org.apache.commons.io.IOUtils
import ru.shadowsparky.client.utils.Extras
import ru.shadowsparky.client.utils.Injection
import java.nio.charset.Charset

class ADBWorker {
    private val log = Injection.provideLogger()
    private var proc: Process? = null

    fun forwardPort(device_id: String) : ADBResult {
        val result = executeCommand(listOf("adb", "-s", device_id, "forward", "tcp:${Extras.FORWARD_PORT}", "tcp:${Extras.PORT}"))
        if (result.isEmpty())
            return ADBResult(ADBStatus.OK)
        return ADBResult(ADBStatus.ERROR, result)
    }

    fun tapToScreen(x: Double, y: Double) : ADBResult {
        val result = executeCommand(listOf("adb", "shell", "input", "tap", "$x", "$y"))
        if (result.isEmpty())
            return ADBResult(ADBStatus.OK)
        return ADBResult(ADBStatus.ERROR, result)
    }

    fun invokeHomeButton() : ADBResult {
        throw NotImplementedError()
    }

    fun invokeBackButton() : ADBResult {
        throw NotImplementedError()
    }

    fun invokeShowApplicationsButtion() : ADBResult {
        throw NotImplementedError()
    }

    // TODO: Проверка на "битые" девайсы и существование adb
    fun getDevices() : ADBResult {
        val result = executeCommand(listOf("adb", "devices", "-l"))
        if (result.isNotEmpty())
            return ADBResult(ADBStatus.OK, result)
        return ADBResult(ADBStatus.ERROR)
    }

    private fun executeCommand(commands: List<String>) : String {
        proc = ProcessBuilder()
                .command(commands)
                .start()
        return IOUtils.toString(proc?.inputStream, Charset.defaultCharset())
    }
}