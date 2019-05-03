/*
 * Created by shadowsparky in 2019
 */

package ru.shadowsparky.client.utils

import javafx.scene.layout.BorderPane
import ru.shadowsparky.client.client.Client
import ru.shadowsparky.client.utils.adb.ADBWorker
import ru.shadowsparky.client.view.Styles
import java.lang.RuntimeException

object Injection {
    private val logger = Logger()
//    private val mNetworkUtils = NetworkUtils()
    fun provideLogger() = logger
    fun provideConverter() = Converter()
    fun provideAdb() = ADBWorker()
    fun provideStyles() = Styles()
//    fun provideInjector(root: BorderPane) = Injector(root)
//    fun provideLauncher() = Launcher()
    fun provideExecutor() = ConsoleExecutor()
}