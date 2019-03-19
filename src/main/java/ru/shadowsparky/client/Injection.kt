package ru.shadowsparky.client

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

        @Throws(RuntimeException::class)
        fun provideIpV4() = mNetworkUtils.getIpv4()
    }
}