/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.view

import com.jfoenix.controls.JFXButton
import com.jfoenix.controls.JFXListView
import com.jfoenix.controls.JFXTabPane
import com.jfoenix.controls.JFXTextField
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.control.ListView
import javafx.scene.control.TabPane
import javafx.scene.control.TextField
import javafx.scene.paint.Color
import ru.shadowsparky.client.utils.Injection
import tornadofx.*

class Styles : Stylesheet() {
    val wrapper by cssclass()
    val _log = Injection.provideLogger()

    val wifiTextField = TextField().apply {
        maxWidth = 300.0
        minWidth = 300.0
        minHeight = 40.0
        style {
//            cssproperty<>()
//            borderColor += box(Color.RED)
            _log.printInfo("WIFITEXTFIELD: ${tabHeaderBackground.render()}")
            backgroundColor += Color.WHITE
            fontSize = Dimension(16.0, Dimension.LinearUnits.px)
            textFill = Color.BLACK
            alignment = Pos.CENTER
        }
    }

    val buttonStyle = JFXButton().apply {
        style {
            backgroundColor += Color.web("#5B5B5D")
            text = "Подключиться"
            textFill = Color.WHITE
        }
        maxWidth = 300.0
        minWidth = 300.0
        minHeight = 50.0
        buttonType = JFXButton.ButtonType.RAISED
    }

    val defaultList = ListView<Label>().apply {
        maxWidth = 300.0
        minWidth = 300.0
        minHeight = 40.0
        maxHeight = 40.0
    }

    val test by cssclass()

    val defaultTabPane = TabPane().apply {
        tabMinHeight = 50.0
        tabMinWidth = 50.0
        minWidth = 600.0
        minHeight = 500.0
    }

    val defaultColor = c("#1B1B22")

    init {
        wrapper {
            padding = box(10.px)
            spacing = 10.px
        }

        test {
            fontSize = Dimension(16.0, Dimension.LinearUnits.px)
            backgroundColor += defaultColor
        }
    }
}