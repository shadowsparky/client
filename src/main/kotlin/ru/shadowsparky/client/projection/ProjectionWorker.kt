/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.projection

import com.google.common.base.Stopwatch
import com.google.protobuf.CodedInputStream
import jdk.internal.util.xml.impl.Input
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.shadowsparky.client.ConnectionType
import ru.shadowsparky.client.exceptions.CorruptedDataException
import ru.shadowsparky.client.interfaces.Resultable
import ru.shadowsparky.client.mvvm.views.VideoView
import ru.shadowsparky.client.objects.Constants.LOCALHOST
import ru.shadowsparky.client.objects.Constants.PORT
import ru.shadowsparky.client.objects.Injection
import ru.shadowsparky.screencast.proto.HandledPictureOuterClass.HandledPicture as HandledPicture
import ru.shadowsparky.screencast.proto.PreparingDataOuterClass.PreparingData
import tornadofx.FX
import tornadofx.get
import tornadofx.observable
import java.io.BufferedInputStream
import java.io.Closeable
import java.io.InputStream
import java.net.Socket
import java.util.*
import kotlin.concurrent.scheduleAtFixedRate

/**
 * Класс, реализующий функции клиента.
 * В нем происходит вывод изображения на экран, получение данных от сервера и декодирование изображения
 *
 * @param handler подробнее: [Resultable]
 * @param addr ip адрес
 * @param port порт устройства
 * @property video подробнее: [VideoView]
 * @property socket сокет, с помощью которого можно соединиться с сервером
 * @property pData proto модель с данными
 * @property saved_data подробнее: [Injection.provideQueue]
 * @property decoder подробнее: [Decoder]
 * @property type подробнее: [ConnectionType]
 * @property handling статус подключения к серверу
 */
open class ProjectionWorker(
        private val handler: Resultable,
        val addr: String,
        private val port: Int = PORT
) : Closeable {
    private val timer = Timer("Finalizer")
    private var video: VideoView? = null
    private var socket: Socket? = null
    private val log = Injection.provideLogger()
    private lateinit var pData: PreparingData
    private var saved_data = Injection.provideQueue()
    private var decoder: Decoder? = null
    private val type: ConnectionType = if (addr == LOCALHOST) ConnectionType.adb else ConnectionType.wifi
    var handling: Boolean = false
        set(value) {
            if (value) {
                saved_data.clear()
                log.printInfo("Handling enabled")
            } else {
                close()
                log.printInfo("Handling disabled and disposed")
            }
            field = value
        }
    private var stream: InputStream? = null

    /**
     * Запуск процесса подключения
     *
     * @return true если подключение установлено, иначе false
     * @see [connectToServer], [upstream]
     */
    fun start() : Boolean {
        if (!connectToServer())
            return false
        upstream()
        return true
    }

    /**
     * Закрытие соединения с сервером
     */
    fun stop() = socket?.close()

    /**
     * Освобождение ресурсов
     */
    override fun close() {
        socket?.close()
        saved_data.clear()
        decoder = null
        video?.dispose()
        timer.cancel()
        System.gc()
        System.runFinalization()
    }

    /**
     * Создание сокета и подключение к серверу
     *
     * @return true если успешно иначе false
     */
    open fun connectToServer() : Boolean {
        try {
            socket = Socket(addr, port)
            stream = socket!!.getInputStream()
            socket!!.soTimeout = 2000
            socket!!.tcpNoDelay = true
        } catch (e: Exception) {
            handler.onError(e)
            return false
        }
        log.printInfo("Connected to the Server")
        return true
    }

    /**
     * Получение данных, необходимых для подготовки к старту проецирования
     *
     * @see [VideoView], [decode]
     * @return true если данные получены, иначе false
     */
    private fun handlePreparingData() : Boolean {
        try {
            val pData = PreparingData.parseDelimitedFrom(stream)
            if (checkPassword(pData)) return correctPasswordCallback(pData)
        } catch(e: Exception){
            handler.onError(e)
            handling = false
        }
        return false
    }

    private fun checkPassword(pData: PreparingData): Boolean {
        if (pData.password == "") {
            return true
        } else {
            handling = false
            handler.onError(CorruptedDataException("Corrupted pData"))
        }
        return false
    }

    private fun correctPasswordCallback(pData: PreparingData): Boolean {
        this@ProjectionWorker.pData = pData
        log.printInfo("True Password")
        video = VideoView(this@ProjectionWorker, FX.messages["projection"], type)
        decode()
        log.printInfo("Data Handling enabled")
        handler.onSuccess()
        socket?.soTimeout = 0
        return true
    }

    /**
     * Получение данных от сервера и обработка
     *
     * @see [handlePreparingData]
     */
    private fun upstream() = GlobalScope.launch(Dispatchers.IO) {
        handling = true
        gcWorker()
        if (!handlePreparingData())
            return@launch
        try {
            handleImage(stream)
        } catch (e: Exception) {
            handler.onError(e)
        } finally {
            handling = false
        }
    }

    private fun gcWorker() {
        timer.scheduleAtFixedRate(0, 30000) {
            System.gc()
            System.runFinalization()
        }
    }

    private fun handleImage(stream: InputStream?) {
        socket?.receiveBufferSize = 50000
        socket?.sendBufferSize = 50000
        var slowCount = 0
        val timer = Stopwatch.createUnstarted()
        while (handling) {
            timer.start()
            val picture = HandledPicture.parseDelimitedFrom(stream)
            saved_data.add(picture.encodedPicture.toByteArray())
            timer.stop()
            val elapsed = timer.elapsed().seconds
            if (elapsed > 1) {
                slowCount++
                log.printInfo("Detected slow internet connection $elapsed")
            }
            if (slowCount >= 3) {
//                video?.raiseError("hgfhjklqweqweqweqweqwe")
            }
            timer.reset()
        }
    }

    /**
     * Декодирование изображения и вывод на экран
     *
     * @see [Decoder]
     */
    fun decode() = GlobalScope.launch(Dispatchers.IO) {
        decoder = Decoder(video!!)
        while (handling) {
            val item = saved_data.take()
            val image = withContext(Dispatchers.IO) { decoder?.decode(item) }
            if (image != null) video?.showImage(image)
        }
    }
}