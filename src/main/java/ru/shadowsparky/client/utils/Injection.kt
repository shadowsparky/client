/*
 * Created by shadowsparky in 2019
 */

package ru.shadowsparky.client.utils

import ru.shadowsparky.client.utils.adb.ADBWorker

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