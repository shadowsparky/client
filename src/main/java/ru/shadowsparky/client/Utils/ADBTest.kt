package ru.shadowsparky.client.Utils

import org.apache.commons.io.IOUtils
import java.nio.charset.Charset

object ADBTest {
    private val log = Injection.provideLogger()
    private var proc: Process? = null

    fun getOutput() : String {
        return IOUtils.toString(proc?.inputStream, Charset.defaultCharset())
    }

    fun parseDevices(str: String) : ArrayList<ADBDevice> {
        val result = ArrayList<ADBDevice>()
        val items = str.split("\n")
        items.forEach {
//            log.printInfo("ITEM: $it")
            if (it.length > 30) {
                val model = it
                        .substringAfter("model:")
                        .substringBefore(" device:")
                val id = it
                        .substringBefore("device")
                        .trim()
                if ((model.isNotEmpty()) and (id.isNotEmpty())) {
                    result.add(ADBDevice(id, model))
                }
                log.printInfo("found device: $model $id")
            }
        }
        return result
    }

    fun executeCommand(commands: List<String>) {
        proc = ProcessBuilder()
                .command(commands)
                .start()
    }
}