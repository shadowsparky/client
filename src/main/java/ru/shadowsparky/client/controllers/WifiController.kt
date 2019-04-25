/*
 * Created by shadowsparky in 2019
 */

package ru.shadowsparky.client.controllers

import javafx.fxml.FXML
import javafx.scene.control.Control
import javafx.scene.control.TextField

class WifiController : BaseConnectionFragment() {
    @FXML protected lateinit var addr: TextField

    @FXML fun initialize() {
        this.init()
        connect.setOnAction {
            if (connect.text == "Подключиться") {
                setLoading(true)
                launcher.launch(ConnectionType.wifi, this, addr.text)
                connect.text = "Отключиться"
            } else {
                launcher.hide()
                connect.text = "Подключиться"
            }
        }
    }
}