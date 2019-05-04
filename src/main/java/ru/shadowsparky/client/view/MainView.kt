/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.view

import javafx.application.Platform
import javafx.stage.StageStyle
import ru.shadowsparky.client.utils.Injection
import ru.shadowsparky.client.utils.Resultable
import ru.shadowsparky.client.views.VideoView
import tornadofx.View

open class MainView : View("Главное меню"), Resultable {
    lateinit var video: VideoView
    val _log = Injection.provideLogger()
    val styles = Injection.provideStyles()

    override fun onSuccess() = Platform.runLater {
        video.openWindow(StageStyle.UNDECORATED)?.apply {
            isFullScreen = true
        }
        _log.printInfo("SUCCESS")
    }

    override fun onError(e: Exception) = Platform.runLater {
        _log.printInfo("EXCEPTION: $e")
    }

    override val root = styles.defaultTabPane.apply {

    }
}