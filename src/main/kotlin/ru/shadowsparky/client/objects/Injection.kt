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

/**
 * Singleton с инъекциями
 */
object Injection {
    /**
     * @see Logger
     */
    private val logger = Logger()

    /**
     * @see Logger
     */
    fun provideLogger() = logger

    /**
     * @see ADBWorker
     */
    fun provideAdb() = ADBWorker()

    /**
     * @see Styles
     */
    fun provideStyles() = Styles()

    /**
     * @return очередь с ByteArray
     */
    fun provideQueue() = LinkedBlockingQueue<ByteArray>()

    /**
     * @see AdbModel
     */
    fun provideAdbModel() = AdbModel()

    /**
     * @see VideoModel
     */
    fun provideVideoModel() = VideoModel()

    /**
     * @see WifiModel
     */
    fun provideWifiModel() = WifiModel()

    /**
     * @see AdbViewModel
     */
    fun provideAdbViewModel(view: AdbView) = AdbViewModel(view)

    /**
     * @see VideoViewModel
     */
    fun provideVideoViewModel(view: VideoView, type: ConnectionType) = VideoViewModel(view, type)

    /**
     * @see WifiViewModel
     */
    fun provideWifiViewModel(view: WifiView) = WifiViewModel(view)

    /**
     * @see ProjectionWorker
     */
    fun provideProjectionWorker(handler: Resultable, addr: String, port: Int = PORT) = ProjectionWorker(handler, addr, port)

    /**
     * @see ConsoleExecutor
     */
    fun provideExecutor() = ConsoleExecutor()
}