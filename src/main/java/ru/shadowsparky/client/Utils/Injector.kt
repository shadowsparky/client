package ru.shadowsparky.client.Utils

import javafx.fxml.FXMLLoader
import javafx.scene.Node
import javafx.scene.layout.BorderPane
import java.io.IOException

class Injector(private val root: BorderPane) {

    fun injectScreen(resource: String, controller: Controllerable) {
        try {
            val loader = FXMLLoader(javaClass.classLoader.getResource(resource))
            loader.setController(controller)
            root.center = loader.load<Any>() as Node
        } catch (exc: IOException) {
            exc.printStackTrace()
            root.center = null
        }
    }

}