/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.mvc.views

import javafx.scene.control.Alert
import javafx.stage.Stage
import org.scijava.nativelib.NativeLoader
import ru.shadowsparky.client.mvc.Styles
import ru.shadowsparky.client.utils.objects.Constants.DEFAULT_LIB
import tornadofx.App
import tornadofx.reloadStylesheetsOnFocus
import java.io.IOException

class Main : App(MainView::class, Styles::class) {
    override fun start(stage: Stage) {
        stage.apply {
            try {
                NativeLoader.loadLibrary(DEFAULT_LIB);
                minWidthProperty().set(600.0)
                minHeightProperty().set(500.0)
                super.start(this)
            } catch(e : IOException) {
                Alert(Alert.AlertType.ERROR, "Невозможно загрузить библиотеку\nдля работы с видео!").show()
                return@apply
            }
        }
    }

    init {
        reloadStylesheetsOnFocus()
    }
}