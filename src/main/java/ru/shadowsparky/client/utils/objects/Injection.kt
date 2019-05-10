/*
 * Created by shadowsparky in 2019
 *
 */

/*
 * Created by shadowsparky in 2019
 */

package ru.shadowsparky.client.utils.objects

import ru.shadowsparky.client.mvc.Styles
import ru.shadowsparky.client.utils.ConsoleExecutor
import ru.shadowsparky.client.utils.Logger
import ru.shadowsparky.client.utils.adb.ADBWorker
import java.util.concurrent.LinkedBlockingQueue

object Injection {
    private val logger = Logger()
    fun provideLogger() = logger
    fun provideAdb() = ADBWorker()
    fun provideStyles() = Styles()
    fun provideQueue() = LinkedBlockingQueue<ByteArray>()
    fun provideExecutor() = ConsoleExecutor()
}