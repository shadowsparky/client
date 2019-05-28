/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.mvvm.views

import javafx.scene.control.Alert
import javafx.stage.Stage
import org.scijava.nativelib.NativeLoader
import ru.shadowsparky.client.mvvm.Styles
import ru.shadowsparky.client.objects.Constants.DEFAULT_LIB
import tornadofx.App
import tornadofx.reloadStylesheetsOnFocus
import java.io.IOException

/**
 * Точка входа приложения
 */
class Main : App(MainView::class, Styles::class) {

    /**
     * Выполняется во время запуска приложения
     */
    override fun start(stage: Stage) {
        stage.apply {
            minWidthProperty().set(600.0)
            minHeightProperty().set(500.0)
        }
        try {
            NativeLoader.loadLibrary(DEFAULT_LIB) // Загрузка OpenCV
            super.start(stage)
        } catch(e : IOException) {
            // Если Java не находит библиотеку, то выводится сообщение о невозможности работы
            // приложения
            Alert(Alert.AlertType.ERROR, "Невозможно загрузить библиотеку\nдля работы с видео!").show()
            return
        }
    }

    init {
        reloadStylesheetsOnFocus()
    }
}