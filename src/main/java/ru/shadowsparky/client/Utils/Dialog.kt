package ru.shadowsparky.client.Utils

import com.jfoenix.controls.JFXDialog
import com.jfoenix.controls.JFXDialogLayout
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.StackPane

class Dialog(val root: StackPane) {
    fun showDialog(title: String, text: String) {
        val content = JFXDialogLayout()
//        content.setHeading(Label(title))
        content.setBody(Label(text))
        val diag = JFXDialog(root, content, JFXDialog.DialogTransition.CENTER)
        val button = Button("Хорошо")
        button.setOnAction { diag.close() }
        content.setActions(button)
        diag.show()
    }
}