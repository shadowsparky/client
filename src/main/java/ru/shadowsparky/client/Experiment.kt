package ru.shadowsparky.client

import java.io.DataInputStream
import java.io.DataOutputStream


class Experiment {
    private val log = Injection.provideLogger()
    private lateinit var process: Process
    private lateinit var mOut: DataOutputStream
    private lateinit var mIn: DataInputStream
    private lateinit var mError: DataInputStream

    fun startProcess() {
        process = ProcessBuilder("ffplay", "-framerate", "60", "-").start()
        mOut = DataOutputStream(process.outputStream)
    }

    fun pushToProc(data: ByteArray) {
        if (process.isAlive) {
            log.printInfo("writing... $data ${data.size}")
            mOut.write(data)
            log.printInfo("data wrote")
        } else {
            log.printInfo("Process killed")
        }
    }

    fun inputReader() = Thread {
//        while (true) {
//            val mInData = ByteArray(1024)
//            val mErrorData = ByteArray(1024)
//            mIn.read(mInData)
//            mError.read(mErrorData)
//            log.printInfo("input: " + String(mInData) + "error: " + String(mErrorData))
//
//        }
    }.start()
}