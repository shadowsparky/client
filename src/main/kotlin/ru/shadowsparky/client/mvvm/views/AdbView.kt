/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.mvvm.views

import javafx.application.Platform
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.geometry.Pos
import javafx.scene.control.Label
import ru.shadowsparky.client.Dialog
import ru.shadowsparky.client.objects.Injection
import tornadofx.*

open class AdbView : BaseView() {
    override val root = styles.getDefaultStackPane()
    private val viewModel = Injection.provideAdbController(this)
    private val devices = styles.getDefaultList()

    init {
        dialog = Dialog(root)
        with(root) {
            vbox {
                this += styles.getLabel("Выберите устройство")
                this += devices.apply {
                    disableProperty().bind(viewModel.isDisable)
                    itemsProperty().bind(viewModel.items)
                    selectionModel.selectedItemProperty().addListener { _, _, nv: Label? ->
                        viewModel.device = nv?.text
                    }
                }
                addClass(styles.wrapper)
                this += styles.getDefaultButton().apply {
                    disableProperty().bind(isLocked)
                    action {
                        viewModel.startProjection()
                    }
                }
                addClass(styles.wrapper)
                this += styles.getDefaultButton().apply {
                    text = "Справка"
                    action {
                        viewModel.showHelp()
                    }
                    style {
                        backgroundColor += styles.defaultColor
                    }
                }
                addClass(styles.wrapper)
                useMaxWidth = true
                alignment = Pos.CENTER
            }
        }
    }

    open fun updateDevices() = Platform.runLater {
        viewModel.updateDevices()
    }
}