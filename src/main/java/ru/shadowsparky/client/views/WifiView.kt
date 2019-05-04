/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.views

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Pos
import javafx.scene.layout.VBox
import ru.shadowsparky.client.controllers.WifiController
import tornadofx.*

class WifiView : BaseView() {
    override val root = VBox()
    var mInputText = SimpleStringProperty("192.168.31.221")
    var mButtonStatus = SimpleBooleanProperty(false)
    var mButtonText = SimpleStringProperty("Подключиться")
    private var controller: WifiController = WifiController(this)

    override fun onSuccess() {
        super.onSuccess()
        mButtonStatus.set(true)
    }

    override fun onError(e: Exception) {
        super.onError(e)
        mButtonStatus.set(false)
    }

    init {
        with(root) {
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