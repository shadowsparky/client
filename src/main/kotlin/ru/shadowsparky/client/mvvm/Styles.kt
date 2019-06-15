/*
 * Created by shadowsparky in 2019
 *
 */

/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.mvvm

import com.jfoenix.controls.JFXButton
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.control.ListView
import javafx.scene.control.TabPane
import javafx.scene.control.TextField
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import ru.shadowsparky.client.objects.Injection
import tornadofx.*

/**
 * Стили приложения
 *
 */
class Styles : Stylesheet() {
    val wrapper by cssclass()
    val _log = Injection.provideLogger()

    /**
     * @return стилизованный Label
     */
    fun getLabel(text: String) = Label(text).apply {
        style {
            fontSize = Dimension(16.0, Dimension.LinearUnits.px)
            textFill = Color.WHITE
        }
    }

    /**
     * @return стилизованный TextField
     */
    fun getDefaultTextField() = TextField().apply {
        maxWidth = 400.0
        minWidth = 400.0
        minHeight = 55.0
        style {
            fontSize = Dimension(16.0, Dimension.LinearUnits.px)
            alignment = Pos.CENTER
        }
    }

    /**
     * @return стилизованный Button
     */
    fun getDefaultButton() = JFXButton().apply {
        style {
            backgroundColor += Color.web("#5B5B5D")
            text = FX.messages["connect"]
            textFill = Color.WHITE
        }
        maxWidth = 400.0
        minWidth = 400.0
        minHeight = 50.0
        buttonType = JFXButton.ButtonType.RAISED
    }

    /**
     * @return стилизованный ListView
     */
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

    /**
     * @return стилизованный TabPane
     */
    fun getDefaultTabPane() = TabPane().apply {
        tabMinHeight = 50.0
        tabMinWidth = 50.0
        minHeight = 560.0
        minWidth = 600.0
        useMaxWidth = true
        useMaxHeight = true
        tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE
    }

    /**
     * @return стилизованный StackPane
     */
    fun getDefaultStackPane() = StackPane().apply {
        useMaxWidth = true
        useMaxHeight = true
        minHeight = 500.0
        minWidth = 500.0
//        minWidth = 600.0
//        minHeight = 500.0
    }

    val css by cssclass()

    val defaultColor = c("#1B1B22")

    init {
        wrapper {
            padding = box(10.px)
            spacing = 10.px
        }

        css {
            fontSize = Dimension(16.0, Dimension.LinearUnits.px)
            backgroundColor += defaultColor
        }
    }
}