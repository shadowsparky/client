/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.mvvm.views

import javafx.application.Platform
import javafx.application.Platform.runLater
import javafx.geometry.Pos
import javafx.scene.control.Label
import ru.shadowsparky.client.Dialog
import ru.shadowsparky.client.mvvm.viewmodels.AdbViewModel
import ru.shadowsparky.client.objects.Injection
import tornadofx.*

/**
 * Фрагмент с разделом ADB
 *
 * @property root главный layout
 * @property viewModel подробнее: [AdbViewModel]
 * @property devices компонент ListView, в котором находятся устройства
 */
open class AdbView : BaseView() {
    override val root = styles.getDefaultStackPane()
    private val viewModel = Injection.provideAdbViewModel(this)
    private val devices = styles.getDefaultList()

    init {
        dialog = Dialog(root) // инициализация диалога
        with(root) {
            vbox {
                this += styles.getLabel(FX.messages["choose_device"])
                this += devices.apply {
                    disableProperty().bind(viewModel.isDisable)
                    itemsProperty().bind(viewModel.items)
                    selectionModel.selectedItemProperty().addListener { _, _, nv: Label? ->
                        viewModel.device = nv?.text
                    }
                }
                addClass(styles.wrapper)
                // Добавление кнопки "Подключиться"
                this += styles.getDefaultButton().apply {
                    disableProperty().bind(isLocked) // биндинг
                    action {
                        viewModel.startProjection() // действие кнопки
                    }
                }
                addClass(styles.wrapper)
                // Добавление кнопки "Справка"
                this += styles.getDefaultButton().apply {
                    text = FX.messages["hint"]
                    action {
                        viewModel.showHelp() // действие кнопки.
                    }
                    style {
                        backgroundColor += styles.defaultColor // цвет кнопки
                    }
                }
                useMaxWidth = true
                alignment = Pos.CENTER
            }
        }
    }

    /**
     * @see AdbViewModel.updateDevices
     */
    fun updateDevices() = runLater {
        viewModel.updateDevices()
    }
}