/*
 * Created by shadowsparky in 2019
 *
 */

/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.mvc

import com.jfoenix.controls.JFXButton
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.control.ListView
import javafx.scene.control.TabPane
import javafx.scene.control.TextField
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import ru.shadowsparky.client.utils.objects.Injection
import tornadofx.*

class Styles : Stylesheet() {
    val wrapper by cssclass()
    val _log = Injection.provideLogger()

    fun getLabel(text: String) = Label(text).apply {
        style {
            fontSize = Dimension(16.0, Dimension.LinearUnits.px)
            textFill = Color.WHITE
        }
    }

    fun getDefaultTextField() = TextField().apply {
        maxWidth = 400.0
        minWidth = 400.0
        minHeight = 55.0
        style {
            fontSize = Dimension(16.0, Dimension.LinearUnits.px)
            alignment = Pos.CENTER
        }
    }

    fun getDefaultButton() = JFXButton().apply {
        style {
            backgroundColor += Color.web("#5B5B5D")
            text = "Подключиться"
            textFill = Color.WHITE
        }
        maxWidth = 400.0
        minWidth = 400.0
        minHeight = 50.0
        buttonType = JFXButton.ButtonType.RAISED
    }

    fun getDefaultList() = ListView<Label>().apply {
        maxWidth = 400.0
        minWidth = 400.0
        minHeight = 60.0
        maxHeight = 60.0
        style {
            textFill = Color.WHITE
            backgroundColor += Color.WHITE
        }
    }


    fun getDefaultTabPane() = TabPane().apply {
        tabMinHeight = 50.0
        tabMinWidth = 50.0
        minHeight = 560.0
        minWidth = 600.0
        useMaxWidth = true
        useMaxHeight = true
        tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE
    }

    fun getDefaultStackPane() = StackPane().apply {
        useMaxWidth = true
        useMaxHeight = true
        minHeight = 500.0
        minWidth = 500.0
//        minWidth = 600.0
//        minHeight = 500.0
    }

    val test by cssclass()

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