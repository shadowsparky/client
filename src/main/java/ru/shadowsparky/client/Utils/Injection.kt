/*
 * Created by shadowsparky in 2019
 */

package ru.shadowsparky.client.Utils

import javafx.scene.layout.BorderPane
import ru.shadowsparky.client.Client.Client
import ru.shadowsparky.client.Client.Decoder
import ru.shadowsparky.screencast.PreparingData
import ru.shadowsparky.screencast.TransferByteArray
import se.vidstige.jadb.JadbConnection
import java.lang.RuntimeException
import java.util.concurrent.LinkedBlockingQueue
import javax.swing.border.Border

class Injection {
    companion object {
        private val logger = Logger()
        private val mNetworkUtils = NetworkUtils()
        fun provideLogger() = logger
        fun provideLinkedBlockingQueue() = LinkedBlockingQueue<TransferByteArray>()
        fun provideConverter() = Converter()
        fun provideAdb() = JadbConnection()
        fun provideClient(callback: ImageCallback, handler: ConnectionHandler, addr: String) = Client(callback, handler, addr)
        fun provideInjector(root: BorderPane) = Injector(root)
        fun provideLauncher() = Launcher()
        @Throws(RuntimeException::class)
        fun provideIpV4() = mNetworkUtils.getIpv4()
    }
}