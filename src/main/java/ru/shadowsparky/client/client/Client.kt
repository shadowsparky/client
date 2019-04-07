/*
 * Created by shadowsparky in 2019
 */

package ru.shadowsparky.client.client

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.shadowsparky.client.utils.Resultable
import ru.shadowsparky.client.utils.Extras.Companion.PORT
import ru.shadowsparky.client.utils.ImageCallback
import ru.shadowsparky.client.utils.Injection
import ru.shadowsparky.screencast.PreparingData
import java.io.*
import java.net.Socket
import java.net.SocketException

class Client(
        private val callback: ImageCallback,
        private val handler: Resultable,
        val addr: String,
        private val port: Int = PORT
) {
    private var socket: Socket? = null
    private val test = Injection.provideLinkedBlockingQueue()
    private val log = Injection.provideLogger()
    private var inStream: ObjectInputStream? = null
    private var decoder: Decoder? = null
    private var inDataStream: DataInputStream? = null
    private lateinit var pData: PreparingData
    var handling: Boolean = false
        set(value) {
            if (value) {
                log.printInfo("Handling enabled")
            } else {
                socket?.close()
                inStream?.close()
                decoder?.dispose()
                
            }
            field = value
        }

    fun start() {
        connectToServer()
    }

    private fun connectToServer() = GlobalScope.launch {
        try {
            socket = Socket(addr, port)
            inStream = ObjectInputStream(BufferedInputStream(socket!!.getInputStream()))
            inDataStream = DataInputStream(BufferedInputStream(socket!!.getInputStream()))
            socket!!.tcpNoDelay = true
        } catch (e: Exception) {
            handler.onError(e)
            return@launch
        }
        log.printInfo("Connected to the Server")
        streamUp()
    }

    private fun streamUp() {
        enableDataHandling()
    }

    private fun enableDataHandling() = GlobalScope.launch(Dispatchers.IO) {
        handling = true
        val obj = inStream!!.readObject()
        if (obj is PreparingData) {
            if (obj.key == "key") {
                pData = obj
                log.printInfo("True Password")
             } else {
                log.printInfo("Incorrect password")
                handling = false
                return@launch
            }
        } else {
            handling = false
            log.printInfo("Handling disabled by: Preparing Data Not Found. Corrupted data")
            return@launch
        }
        log.printInfo("Data Handling enabled")
//        decoder()
        handler.onSuccess()
        try {
            decoder = Decoder(callback)
            while (handling) {
                val len = inDataStream!!.readInt()
                if (len > 0) {
                    val buf = ByteArray(len)
                    inDataStream!!.readFully(buf, 0, buf.size)
                    decoder?.decode(buf)
                }
            }
        } catch (e: SocketException) {
            log.printInfo("Handling disabled by: SocketException. ${e.message}")
        } catch (e: RuntimeException) {
            log.printInfo("Handling disabled by: RuntimeException. ${e.message}")
            handler.onError(e)
        } catch (e: EOFException) {
            log.printInfo("Handling disabled by: EOFException. ${e.message}")
            handler.onError(e)
        } catch (e: OptionalDataException) {
            log.printInfo("Handling disabled by: OptionalDataException. ${e.message}")
            handler.onError(e)
        } finally {
            handling = false
        }
    }
}