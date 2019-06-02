/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.mvvm.viewmodels

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.control.Label
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ticker
import ru.shadowsparky.client.Dialog
import ru.shadowsparky.client.mvvm.models.AdbModel
import ru.shadowsparky.client.mvvm.views.AdbView
import ru.shadowsparky.client.mvvm.views.BaseView
import ru.shadowsparky.client.objects.Constants.FAQ
import ru.shadowsparky.client.objects.Constants.FAQ_MESSAGE
import ru.shadowsparky.client.objects.Constants.FORWARD_PORT
import ru.shadowsparky.client.objects.Constants.LOCALHOST
import ru.shadowsparky.client.objects.Injection
import ru.shadowsparky.client.projection.ProjectionWorker
import tornadofx.ViewModel
import tornadofx.fail
import tornadofx.success
import java.util.*
import kotlin.concurrent.schedule

/**
 * ViewModel из MVVM для работы с ADB
 *
 * @param view подробнее: [AdbView]
 * @param model подробнее: [AdbModel]
 * @property isDisable проперти, к которому привязывается состояние isDisabled в ListView
 * @property device наименование выбранного устройства в ListView
 * @property items проперти, к которому привязываются элементы из списка ListView
 */
@UseExperimental(ObsoleteCoroutinesApi::class)
open class AdbViewModel(
        private val view: AdbView,
        private val model: AdbModel = Injection.provideAdbModel()
) : ViewModel() {
    val isDisable = SimpleBooleanProperty(false)
    var device: String? = null
    val items = SimpleObjectProperty<ObservableList<Label>>()

    init {
        items.set(FXCollections.observableArrayList()) // инициализация списка
    }

    /**
     * Получение списка устройств, подключенных по ADB
     */
    fun updateDevices() = runBlocking {
        try {
            items.get().clear()
            val devices = model.getDevicesRequest()
            devices.forEach { items.get().add(Label("$it")) }
            isDisable.set(false)
        } catch (e: Exception) {
            items.get().add(Label(e.message))
            isDisable.set(true)
        }
    }
//    fun updateDevices() = runAsync {
//        model.getDevicesRequest()
//    }.apply {
//        setOnRunning {
//             очистка предыдущего списка
//            items.get().clear()
//        }
//        success {
//             добавление полученных устройств в ListView
//         /   it.forEach { items.get().add(Label("$it")) }
//            isDisable.set(false) // включение взаимодействия с ListView
//        } fail {
//            items.get().add(Label(it.message)) // добавление ошибки в ListView
//            isDisable.set(true) // отключение взаимодействия с ListView
//        }
//    }

    /**
     * Отображение справки
     *
     * @see [Dialog]
     */
    fun showHelp() = view.dialog.showDialog(FAQ, FAQ_MESSAGE, true)

    /**
     * Переопределение порта
     *
     * @see AdbModel.forwardPort, [ProjectionWorker]
     */
    private fun forwardPort() = runAsync {
        model.forwardPort(device!!)
    } success {
        view.projection = ProjectionWorker(view, LOCALHOST, FORWARD_PORT)
        view.projection?.start()
    } fail {
        view.onError(it as Exception)
    }

    /**
     * Запуск проецирования
     *
     * @see AdbModel.checkDevice
     */
    open fun startProjection() = runAsync {
        model.checkDevice(device) // Проверка устройства
    }.apply {
        setOnRunning {
            BaseView.isLoaded.value = false
            BaseView.isLocked.value = true
        }
        success {
            forwardPort() // если устройство корректное, то выполняется переопределение порта
        } fail {
            view.onError(it as Exception) // если некорректное, то ошибка
        }
    }
}