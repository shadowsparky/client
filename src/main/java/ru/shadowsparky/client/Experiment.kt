package ru.shadowsparky.client

import java.io.FileOutputStream


class Experiment {
    private val log = Injection.provideLogger()
    private val fos = FileOutputStream("/home/eugene/Desktop/test/output.mpg", true)

    fun writeToFile(bytes: ByteArray) {
        fos.write(bytes)
        fos.flush()
    }

    fun close() {
        fos.close()
    }
//    private lateinit var process: Process

    fun startProcess() = Thread{
//        process = ProcessBuilder("ffplay", "-framerate", "60", "-").start()

//        mOut = process.outputStream
//        mIn = BufferedReader(InputStreamReader(process.inputStream))
//        mError = BufferedReader(InputStreamReader(process.errorStream))
//        inputReader()
    }.start()

    @Synchronized fun pushToProc(data: ByteArray, c: Int) = Thread {
//        if (process.isAlive) {
//            var bytes = ByteArrayOutputStream()
//            log.printInfo("$data $c")
//            bytes.write(data, 0, c)
//            bytes.close()
//            process.waitFor()
//        } else {
//            mOut.close()
//            log.printInfo("Process killed")
//        }
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