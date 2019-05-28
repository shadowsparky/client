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
import ru.shadowsparky.client.mvvm.views.AdbView
import ru.shadowsparky.client.mvvm.views.BaseView
import ru.shadowsparky.client.interfaces.ViewModelable
import ru.shadowsparky.client.mvvm.models.AdbModel
import ru.shadowsparky.client.objects.Constants.FAQ
import ru.shadowsparky.client.objects.Constants.FAQ_MESSAGE
import ru.shadowsparky.client.objects.Constants.FORWARD_PORT
import ru.shadowsparky.client.objects.Constants.LOCALHOST
import ru.shadowsparky.client.objects.Injection
import ru.shadowsparky.client.projection.ProjectionWorker
import tornadofx.Controller
import tornadofx.fail
import tornadofx.success

open class AdbViewModel(
        private val view: AdbView,
        private val model: AdbModel = Injection.provideAdbModel()
) : Controller(), ViewModelable {
    private val _log = Injection.provideLogger()
    private val styles = Injection.provideStyles()
    val isDisable = SimpleBooleanProperty(false)
    var device: String? = null
    val items = SimpleObjectProperty<ObservableList<Label>>()

    init {
        items.set(FXCollections.observableArrayList())
    }

    fun updateDevices() = runAsync {
        model.getDevicesRequest()
    }.apply {
        setOnRunning {
            items.get().clear()
        }
        success  {
            it.forEach { items.get().add(Label("$it")) }
            isDisable.set(false)
        } fail {
            items.get().add(Label(it.message))
            isDisable.set(true)
        }
    }

    fun showHelp() = view.dialog.showDialog(FAQ, FAQ_MESSAGE, true)

    private fun forwardPort() = runAsync {
        model.forwardPort(device!!)
    } success {
        view.projection = ProjectionWorker(view, LOCALHOST, FORWARD_PORT)
        view.projection?.start()
    } fail {
        view.onError(it as Exception)
    }

    open fun startProjection() = runAsync {
        model.checkDevice(device)
    }.apply {
        setOnRunning {
            BaseView.isLoaded.value = false
        }
        success {
            forwardPort()
        } fail {
            view.onError(it as Exception)
        }
    }
}