/*
 * Created by shadowsparky in 2019
 *
 */

/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.views

import javafx.application.Platform
import javafx.scene.Parent
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.stage.StageStyle
import jfxtras.styles.jmetro8.JMetro
import ru.shadowsparky.client.utils.Injection
import ru.shadowsparky.client.utils.Resultable
import tornadofx.View

abstract class BaseView : View(""), Resultable {
    abstract override val root: Parent
    protected val styles = Injection.provideStyles()
    var video: VideoView? = null

    protected fun setStyle() {
        JMetro(JMetro.Style.DARK).applyTheme(root)
    }

    override fun onSuccess() = Platform.runLater {
        video!!.stage = video!!.openWindow(StageStyle.UNDECORATED)?.apply {
            isFullScreen = true
        }
        video!!.stage?.addEventFilter(KeyEvent.KEY_PRESSED) {
            if (it.code == KeyCode.ESCAPE) {
                log.info("Escape")
                video!!.client?.close()
                video!!.close()
            }
        }
        log.info("onSuccess")
    }

    override fun onError(e: Exception) = Platform.runLater {
        log.info("onError: $e")
    }
}