/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.mvc.views

import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Pos
import javafx.scene.control.ProgressBar
import javafx.scene.control.ProgressIndicator
import ru.shadowsparky.client.mvc.controllers.WifiController
import ru.shadowsparky.client.utils.Dialog
import ru.shadowsparky.client.utils.objects.Injection
import tornadofx.*

class WifiView : BaseView() {
    override val root = styles.getDefaultStackPane()
    var mInputText = SimpleStringProperty("192.168.31.221")
    private var controller = Injection.provideWifiController(this)

    init {
        root.apply {
            vbox {
                this += styles.getLabel("Введите IP адрес")
                this += styles.getDefaultTextField().apply {
                    bind(mInputText)
                }
                addClass(styles.wrapper)
                this += styles.getDefaultButton().apply {
                    bind(mButtonText)
                    disableProperty().bind(isLocked)
                    action { controller.startProjection() }
                }
                this += ProgressBar().apply {
                    visibleProperty().bind(!this@WifiView.isLoaded)
                    progress = ProgressIndicator.INDETERMINATE_PROGRESS
                }
                useMaxWidth = true
                alignment = Pos.CENTER
            }
        }
        dialog = Dialog(root)
    }
}