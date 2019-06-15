/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.mvvm.views

import javafx.application.Platform
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import javafx.scene.layout.StackPane
import ru.shadowsparky.client.Dialog
import ru.shadowsparky.client.exceptions.EmptyAddressException
import ru.shadowsparky.client.exceptions.ForwardException
import ru.shadowsparky.client.objects.Injection
import ru.shadowsparky.client.interfaces.Resultable
import ru.shadowsparky.client.exceptions.IncorrectPasswordException
import ru.shadowsparky.client.exceptions.ProjectionAlreadyStartedException
import ru.shadowsparky.client.mvvm.Styles
import ru.shadowsparky.client.projection.ProjectionWorker
import tornadofx.FX
import tornadofx.View
import tornadofx.get
import java.io.EOFException
import java.net.ConnectException
import java.net.UnknownHostException

/**
 *  Основа для AdbView и WifiView.
 *  В ней собраны все общие методы и переменные
 *
 *  @see [Resultable]
 *  @property styles подробнее: [Styles]
 *  @property projection подробнее: [ProjectionWorker]
 *  @property dialog подробнее: [Dialog]
 *  @property isLocked статус блокировки кнопки
 *  @property isLoaded статус загрузки
 *  @property mButtonText проперти для привязки к кнопке для подключения
 */
abstract class BaseView : View(""), Resultable {
    abstract override val root: StackPane
    protected val styles = Injection.provideStyles()
    var projection: ProjectionWorker? = null
    lateinit var dialog: Dialog
    companion object {
        var isLocked = SimpleBooleanProperty(false)
        var isLoaded = SimpleBooleanProperty(true)
    }
    var mButtonText = SimpleStringProperty(FX.messages["connect"])

    /**
     * @see Resultable
     */
    override fun onSuccess() = Platform.runLater {
        isLoaded.value = true
        isLocked.value = true
    }

    /**
     * @see Resultable
     */
    override fun onError(e: Exception) = Platform.runLater {
        isLocked.value = false
        isLoaded.value = true
        log.info("onError: $e")
        val error = when (e) {
            is ConnectException, is UnknownHostException-> FX.messages["server_not_found"]
            is EOFException -> FX.messages["disconnected_from_server"]
            is EmptyAddressException, is ForwardException -> e.message!!
            is ProjectionAlreadyStartedException -> FX.messages["previous_not_disconnected"]
            is IncorrectPasswordException -> FX.messages["incorrect_password"]
            else -> FX.messages["broken_connection"]
        }
        dialog.showDialog(FX.messages["error"], error, true)
    }
}