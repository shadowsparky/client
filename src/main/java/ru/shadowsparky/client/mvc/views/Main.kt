/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.mvc.views

import javafx.stage.Stage
import ru.shadowsparky.client.mvc.Styles
import tornadofx.App
import tornadofx.reloadStylesheetsOnFocus

class Main : App(MainView::class, Styles::class) {
    override fun start(stage: Stage) {
        stage.apply {
            minWidthProperty().set(600.0)
            minHeightProperty().set(500.0)
//            maxWidthProperty().set(600.0)
//            maxHeightProperty().set(600.0)
            super.start(this)
        }
    }

    init {
        reloadStylesheetsOnFocus()
    }
}