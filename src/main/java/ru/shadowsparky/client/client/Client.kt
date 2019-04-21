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
import ru.shadowsparky.client.utils.exceptions.IncorrectPasswordException
import java.io.*
import java.net.Socket
import java.net.SocketException

@Suppress("BlockingMethodInNonBlockingContext")
class Client(
        private val callback: ImageCallback,
        private val handler: Resultable,
        val addr: String,
        private val port: Int = PORT
) {
    private var socket: Socket? = null
    private val log = Injection.provideLogger()
    private var decoder: Decoder? = null
    private lateinit var pData: PreparingDataOuterClass.PreparingData
    var handling: Boolean = false
        set(value) {
            if (value) {
                log.printInfo("Handling enabled")
            } else {
                socket?.close()
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
        val pData = PreparingDataOuterClass.PreparingData.parseDelimitedFrom(socket?.getInputStream())
        if (pData.password == "") {
            this@Client.pData = pData
            log.printInfo("True Password")
        } else {
            log.printInfo("Incorrect password")
            handling = false
            handler.onError(IncorrectPasswordException())
            return@launch
        }
        log.printInfo("Data Handling enabled")
        handler.onSuccess()
        try {
            decoder = Decoder(callback)
            log.printInfo("Decoder initialized")
            while (handling) {
                log.printInfo("Handling new iteration")
                val picture = HandledPictureOuterClass
                        .HandledPicture
                        .parseDelimitedFrom(socket!!.getInputStream())
                decoder?.decode(picture.encodedPicture.toByteArray())
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