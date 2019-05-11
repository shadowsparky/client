/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.mvc.views

import javafx.application.Platform
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import javafx.scene.layout.StackPane
import ru.shadowsparky.client.utils.Dialog
import ru.shadowsparky.client.utils.objects.Injection
import ru.shadowsparky.client.utils.interfaces.Resultable
import ru.shadowsparky.client.utils.exceptions.IncorrectPasswordException
import ru.shadowsparky.client.utils.exceptions.ProjectionAlreadyStartedException
import ru.shadowsparky.client.utils.projection.ProjectionWorker
import tornadofx.View
import tornadofx.onChange
import java.io.EOFException
import java.net.ConnectException

abstract class BaseView : View(""), Resultable {
    abstract override val root: StackPane
    protected val styles = Injection.provideStyles()
    var projection: ProjectionWorker? = null
    lateinit var dialog: Dialog
    companion object {
        val isLocked = SimpleBooleanProperty(false)
        val isLoaded = SimpleBooleanProperty(true)
    }
    var mButtonText = SimpleStringProperty("Подключиться")

    override fun onSuccess() = Platform.runLater {
        isLoaded.value = true
        projection?.showProjection()
        isLocked.value = true
    }

    override fun onError(e: Exception) = Platform.runLater {
        isLocked.value = false
        isLoaded.value = true
        log.info("onError: $e")
        val error = when (e) {
            is ConnectException -> "При соединении произошла ошибка.\nСервер не найден"
            is EOFException -> "Произошло отключение от сервера"
            is ProjectionAlreadyStartedException -> "При соединении произошла ошибка. \nВы не отключились от предыдущего соединения"
            is IncorrectPasswordException -> "При соединении с сервером произошла ошибка. Вы ввели неправильный пароль"
            else -> "Соединение было разорвано"
        }
        dialog.showDialog("Ошибка", error, true)
    }
}