package ru.shadowsparky.client

import java.io.BufferedReader
import java.io.ByteArrayOutputStream
import java.io.InputStreamReader
import java.io.OutputStream


class Experiment {
    private val log = Injection.provideLogger()
    private lateinit var process: Process
    private lateinit var mOut: OutputStream
    private lateinit var mIn: BufferedReader
    private lateinit var mError: BufferedReader

    fun startProcess() = Thread{
        process = ProcessBuilder("ffplay", "-framerate", "60", "-").start()
        mOut = process.outputStream
        mIn = BufferedReader(InputStreamReader(process.inputStream))
        mError = BufferedReader(InputStreamReader(process.errorStream))
//        inputReader()
    }.start()
    @Deprecated("Без результата")
    @Synchronized fun pushToProc(data: ByteArray, c: Int) = Thread {
        if (process.isAlive) {
            var bytes = ByteArrayOutputStream()
            log.printInfo("$data $c")
            bytes.write(data, 0, c)
            bytes.writeTo(mOut)
            bytes.close()
            process.waitFor()
        } else {
            mOut.close()
            log.printInfo("Process killed")
        }
    }.start()

    fun inputReader() = Thread {
        var line: String
        while (true) {
//            line = mIn.readLine()
//            log.printInfo("Process Input: $line")
//            line = mError.readLine()
//            log.printError("Process Error: $line")
        }
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