package ru.shadowsparky.client

import java.io.ByteArrayOutputStream
import java.io.DataOutputStream

class Experiment {
    private val log = Injection.provideLogger()
    private val bytes = ByteArrayOutputStream()
    private val mOut = DataOutputStream(bytes)
    private var process: Process = ProcessBuilder("ffplay", "-framerate", "60", "-").start()

    @Synchronized fun pushToProc(data: ByteArray) {
        if (process.isAlive) {
            bytes.reset()
            mOut.write(data)
            log.printInfo("Writing...")
            bytes.writeTo(process.outputStream)
            log.printInfo("Wrote")
        } else {
            log.printError("PROCESS KILLED")
        }
    }
}