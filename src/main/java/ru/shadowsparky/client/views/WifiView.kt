/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.views

import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Pos
import javafx.scene.layout.StackPane
import ru.shadowsparky.client.controllers.WifiController
import ru.shadowsparky.client.utils.Dialog
import tornadofx.*

class WifiView : BaseView() {
    override val root = StackPane()
    var mInputText = SimpleStringProperty("192.168.31.221")
    private var controller: WifiController = WifiController(this)

    init {
        root.apply {
            vbox {
                this += styles.getLabel("Введите IP адрес")
                this += styles.wifiTextField.apply {
                    bind(mInputText)
                }
                addClass(styles.wrapper)
                this += styles.buttonStyle.apply {
                    bind(mButtonText)
                    isDisable.toProperty().bind(mButtonStatus)
                    action { controller.startProjection() }
                }
                useMaxWidth = true
                alignment = Pos.CENTER
            }
        }
        dialog = Dialog(root)
    }
}