/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.views

import javafx.application.Platform
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import javafx.scene.Parent
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.stage.StageStyle
import jfxtras.styles.jmetro8.JMetro
import ru.shadowsparky.client.utils.ConnectionType
import ru.shadowsparky.client.utils.Injection
import ru.shadowsparky.client.utils.Resultable
import tornadofx.View

abstract class BaseView : View(""), Resultable {
    abstract override val root: Parent
    protected val styles = Injection.provideStyles()
    var video: VideoView? = null
    var mButtonStatus = SimpleBooleanProperty(false)
    var mButtonText = SimpleStringProperty("Подключиться")

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
            }
        }
        if (video?.controller?.type == ConnectionType.adb)
            video?.controller?.enableADBActions()
        log.info("button disabled")
        mButtonStatus.set(true)
        log.info("onSuccess")
    }

    override fun onError(e: Exception) = Platform.runLater {
        mButtonStatus.set(false)
        video?.stage?.close()
        log.info("button enabled")
        log.info("onError: $e")
    }
}