/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.mvvm.views

import javafx.geometry.Pos
import ru.shadowsparky.client.Dialog
import ru.shadowsparky.client.mvvm.viewmodels.WifiViewModel
import ru.shadowsparky.client.objects.Injection
import tornadofx.*

/**
 * Фрагмент с разделом Wifi
 * @property root главный layout
 * @property viewModel подробнее: [WifiViewModel]
 */
class WifiView : BaseView() {
    override val root = styles.getDefaultStackPane()
    private var viewModel = Injection.provideWifiViewModel(this)

    init {
        root.apply {
            vbox {
                this += styles.getLabel("Введите IP адрес")
                this += styles.getDefaultTextField().apply {
                    bind(viewModel.mDeviceAddr)
                }
                addClass(styles.wrapper)
                this += styles.getDefaultButton().apply {
                    bind(mButtonText)
                    disableProperty().bind(isLocked)
                    action { viewModel.startProjection() }
                }
                useMaxWidth = true
                alignment = Pos.CENTER
            }
        }
        dialog = Dialog(root)
    }
}