/*
 * Created by shadowsparky in 2019
 *
 */

/*
 * Created by shadowsparky in 2019
 */

package ru.shadowsparky.client.objects

import ru.shadowsparky.client.mvvm.Styles
import ru.shadowsparky.client.mvvm.viewmodels.AdbViewModel
import ru.shadowsparky.client.mvvm.viewmodels.VideoViewModel
import ru.shadowsparky.client.mvvm.viewmodels.WifiViewModel
import ru.shadowsparky.client.mvvm.models.WifiModel
import ru.shadowsparky.client.mvvm.models.AdbModel
import ru.shadowsparky.client.mvvm.models.VideoModel
import ru.shadowsparky.client.mvvm.views.AdbView
import ru.shadowsparky.client.mvvm.views.VideoView
import ru.shadowsparky.client.mvvm.views.WifiView
import ru.shadowsparky.client.ConnectionType
import ru.shadowsparky.client.ConsoleExecutor
import ru.shadowsparky.client.Logger
import ru.shadowsparky.client.adb.ADBWorker
import ru.shadowsparky.client.interfaces.Resultable
import ru.shadowsparky.client.objects.Constants.PORT
import ru.shadowsparky.client.projection.ProjectionWorker
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
    fun provideAdbController(view: AdbView) = AdbViewModel(view)
    fun provideVideoController(view: VideoView, type: ConnectionType) = VideoViewModel(view, type)
    fun provideWifiController(view: WifiView) = WifiViewModel(view)
    fun provideProjectionWorker(handler: Resultable, addr: String, port: Int = PORT) = ProjectionWorker(handler, addr, port)
    fun provideExecutor() = ConsoleExecutor()
}