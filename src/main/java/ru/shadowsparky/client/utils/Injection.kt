/*
 * Created by shadowsparky in 2019
 */

package ru.shadowsparky.client.utils

import javafx.scene.layout.BorderPane
import ru.shadowsparky.client.client.Client
import ru.shadowsparky.client.utils.adb.ADBWorker
import ru.shadowsparky.screencast.TransferByteArray
import java.lang.RuntimeException
import java.util.concurrent.LinkedBlockingQueue

class Injection {
    companion object {
        private val logger = Logger()
        private val mNetworkUtils = NetworkUtils()
        fun provideLogger() = logger
        fun provideLinkedBlockingQueue() = LinkedBlockingQueue<TransferByteArray>()
        fun provideConverter() = Converter()
        fun provideAdb() = ADBWorker()
        fun provideClient(callback: ImageCallback, handler: Resultable, addr: String) = Client(callback, handler, addr)
        fun provideInjector(root: BorderPane) = Injector(root)
        fun provideLauncher() = Launcher()
        @Throws(RuntimeException::class)
        fun provideIpV4() = mNetworkUtils.getIpv4()
    }
}