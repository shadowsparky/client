/*
 * Created by shadowsparky in 2019
 */

package ru.shadowsparky.client.utils

import com.jfoenix.controls.JFXDialog
import com.jfoenix.controls.JFXDialogLayout
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.StackPane

class Dialog(val root: StackPane) {
    fun showDialog(title: String, text: String, showTitle: Boolean = false) {
        val content = JFXDialogLayout()
        if (showTitle)
            content.setHeading(Label(title))
        content.setBody(Label(text))
        val diag = JFXDialog(root, content, JFXDialog.DialogTransition.CENTER, true)
        val button = Button("Хорошо")
        button.setOnAction { diag.close() }
        content.setActions(button)
        diag.show()
    }
}