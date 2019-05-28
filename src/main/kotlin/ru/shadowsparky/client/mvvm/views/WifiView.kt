/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.mvvm.views

import javafx.geometry.Pos
import ru.shadowsparky.client.Dialog
import ru.shadowsparky.client.objects.Injection
import tornadofx.*

class WifiView : BaseView() {
    override val root = styles.getDefaultStackPane()
    private var viewModel = Injection.provideWifiController(this)

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
//                this += ProgressBar().apply {
//                    visibleProperty().bind(!isLoaded)
//                    progress = ProgressIndicator.INDETERMINATE_PROGRESS
//                }
                useMaxWidth = true
                alignment = Pos.CENTER
            }
        }
        dialog = Dialog(root)
    }
}