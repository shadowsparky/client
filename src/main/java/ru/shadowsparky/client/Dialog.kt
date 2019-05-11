/*
 * Created by shadowsparky in 2019
 */

package ru.shadowsparky.client

import com.jfoenix.controls.JFXDialog
import com.jfoenix.controls.JFXDialogLayout
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.StackPane
import javafx.scene.text.Font

class Dialog(val root: StackPane) {
    fun showDialog(title: String, text: String, showTitle: Boolean = false) {
        val content = JFXDialogLayout()
        val title_lab = Label(title)
        title_lab.font = Font(20.0)
        if (showTitle)
            content.setHeading(title_lab)
        content.setBody(Label(text))
        val diag = JFXDialog(root, content, JFXDialog.DialogTransition.CENTER, true)
        val button = Button("Хорошо")
        button.setOnAction { diag.close() }
        content.setActions(button)
        button.requestFocus()
        diag.show()
    }
}