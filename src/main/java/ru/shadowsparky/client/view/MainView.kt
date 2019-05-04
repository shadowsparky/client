/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.view

import javafx.application.Platform
import javafx.stage.StageStyle
import jfxtras.styles.jmetro8.JMetro
import ru.shadowsparky.client.client.Client
import ru.shadowsparky.client.utils.Injection
import ru.shadowsparky.client.utils.Resultable
import tornadofx.View
import tornadofx.addClass
import tornadofx.tab

open class MainView : View("test"), Resultable {
    lateinit var video: VideoView
    val _log = Injection.provideLogger()
    val styles = Injection.provideStyles()

    override fun onSuccess() = Platform.runLater{
        video.openWindow(StageStyle.UNDECORATED)?.apply {
            isFullScreen = true
        }
        _log.printInfo("SUCCESS")
    }

    override fun onError(e: Exception) = Platform.runLater {
        _log.printInfo("EXCEPTION: $e")
    }

    override val root = styles.defaultTabPane.apply {
        addClass(styles.test)

        tab("WIFI") {
            add(WifiView(this@MainView).root)
        }

        tab("ADB") {
            val adb = AdbView(this@MainView)
            adb.updateDevices()
            add(adb.root)
        }
    }

    init {
        JMetro(JMetro.Style.DARK).applyTheme(root)
    }
}