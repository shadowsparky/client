/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.mvc.views

import javafx.application.Platform
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.control.ProgressBar
import javafx.scene.control.ProgressIndicator.INDETERMINATE_PROGRESS
import ru.shadowsparky.client.Dialog
import ru.shadowsparky.client.objects.Injection
import tornadofx.*

class AdbView : BaseView() {
    override val root = styles.getDefaultStackPane()
    val isDisable = SimpleBooleanProperty(false)
    private val items = SimpleObjectProperty<ObservableList<Label>>()
    var input = styles.getDefaultList()
    private val controller = Injection.provideAdbController(this)
    var deviceAddr: String? = null

    fun updateDevices() = Platform.runLater {
        controller.updateDevices()
    }

    fun addDevice(device: String) = items.get().add(Label(device))
    fun addAllDevices(devices: ObservableList<Label>) { items.value = devices }
    fun clearDevices() = input.items.clear()

    init {
        items.set(FXCollections.observableArrayList())
        dialog = Dialog(root)
        with(root) {
            vbox {
                this += styles.getLabel("Выберите устройство")
                this += input.apply {
                    disableProperty().bind(this@AdbView.isDisable)
                    itemsProperty().bind(this@AdbView.items)
                    selectionModel.selectedItemProperty().addListener { _, _, nv: Label? ->
                        deviceAddr = nv?.text
                    }
                }
                addClass(styles.wrapper)
                this += styles.getDefaultButton().apply {
                    disableProperty().bind(isLocked)
                    action {
                        controller.startProjection()
                    }
                }
                addClass(styles.wrapper)
                this += styles.getDefaultButton().apply {
                    text = "Справка"
                    action {
                        controller.showHelp()
                    }
                    style {
                        backgroundColor += styles.defaultColor
                    }
                }
                addClass(styles.wrapper)
                this += ProgressBar().apply {
                    visibleProperty().bind(!isLoaded)
                    progress = INDETERMINATE_PROGRESS
                }
                useMaxWidth = true
                alignment = Pos.CENTER
            }
        }
    }
}