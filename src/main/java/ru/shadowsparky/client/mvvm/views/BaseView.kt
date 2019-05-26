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
import ru.shadowsparky.client.projection.ProjectionWorker
import tornadofx.View
import java.io.EOFException
import java.net.ConnectException
import java.net.UnknownHostException

abstract class BaseView : View(""), Resultable {
    abstract override val root: StackPane
    protected val styles = Injection.provideStyles()
    var projection: ProjectionWorker? = null
    lateinit var dialog: Dialog
    companion object {
        var isLocked = SimpleBooleanProperty(false)
        var isLoaded = SimpleBooleanProperty(true)
    }
    var mButtonText = SimpleStringProperty("Подключиться")

    override fun onSuccess() = Platform.runLater {
        isLoaded.value = true
        isLocked.value = true
    }

    override fun onError(e: Exception) = Platform.runLater {
        isLocked.value = false
        isLoaded.value = true
        log.info("onError: $e")
        val error = when (e) {
            is ConnectException, is UnknownHostException-> "При соединении произошла ошибка.\nСервер не найден"
            is EOFException -> "Произошло отключение от сервера"
            is EmptyAddressException -> e.message!!
            is ForwardException -> e.message!!

            is ProjectionAlreadyStartedException -> "При соединении произошла ошибка. \nВы не отключились от предыдущего соединения"
            is IncorrectPasswordException -> "При соединении с сервером произошла ошибка. Вы ввели неправильный пароль"
            else -> "Соединение было разорвано"
        }
        dialog.showDialog("Ошибка", error, true)
    }
}