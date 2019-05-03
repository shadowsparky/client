/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.view

import com.jfoenix.controls.JFXTabPane
import javafx.application.Platform
import javafx.stage.StageStyle
import ru.shadowsparky.client.utils.Injection
import ru.shadowsparky.client.utils.Resultable
import tornadofx.*

class MainView : View("test"), Resultable {
    lateinit var video: VideoView
    private val _log = Injection.provideLogger()

    override fun onSuccess() = Platform.runLater{
        video.openWindow(StageStyle.UNDECORATED)?.apply {
            isFullScreen = true
        }
        _log.printInfo("SUCCESS")
    }

    override fun onError(e: Exception) = Platform.runLater {
        _log.printInfo("EXCEPTION: $e")
    }

    override val root = JFXTabPane().apply {
        tabMinHeight = 50.0
        tabMaxHeight = 50.0
        style {
            fontSize = Dimension(16.0, Dimension.LinearUnits.px)
        }

        tab("WIFI") {
            add(WifiView(this@MainView).root)
        }

        tab("ADB") {
            val adb = AdbView(this@MainView)
            adb.updateDevices()
            add(adb.root)
        }
    }
}