/*
 * Created by shadowsparky in 2019
 *
 */

/*
 * Created by shadowsparky in 2019
 */

package ru.shadowsparky.client.objects

import ru.shadowsparky.client.mvc.Styles
import ru.shadowsparky.client.mvc.controllers.AdbController
import ru.shadowsparky.client.mvc.controllers.VideoController
import ru.shadowsparky.client.mvc.controllers.WifiController
import ru.shadowsparky.client.mvc.models.WifiModel
import ru.shadowsparky.client.mvc.models.AdbModel
import ru.shadowsparky.client.mvc.models.VideoModel
import ru.shadowsparky.client.mvc.views.AdbView
import ru.shadowsparky.client.mvc.views.VideoView
import ru.shadowsparky.client.mvc.views.WifiView
import ru.shadowsparky.client.ConnectionType
import ru.shadowsparky.client.ConsoleExecutor
import ru.shadowsparky.client.Logger
import ru.shadowsparky.client.adb.ADBWorker
import java.util.concurrent.LinkedBlockingQueue

object Injection {
    private val logger = Logger()
    fun provideLogger() = logger
    fun provideAdb() = ADBWorker()
    fun provideStyles() = Styles()
    fun provideQueue() = LinkedBlockingQueue<ByteArray>()
    fun provideAdbModel() = AdbModel()
    fun provideVideoModel() = VideoModel()
    fun provideWifiModel() = WifiModel()
    fun provideAdbController(view: AdbView) = AdbController(view)
    fun provideVideoController(view: VideoView, type: ConnectionType) = VideoController(view, type)
    fun provideWifiController(view: WifiView) = WifiController(view)

    fun provideExecutor() = ConsoleExecutor()
}