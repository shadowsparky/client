/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.views

import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Pos
import javafx.scene.layout.VBox
import ru.shadowsparky.client.controllers.WifiController
import tornadofx.*

class WifiView : BaseView() {
    override val root = VBox()
    var mInputText = SimpleStringProperty("192.168.31.221")
    private var controller: WifiController = WifiController(this)

    init {
        root.apply {
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
}