package ru.shadowsparky.client

import ru.shadowsparky.screencast.TransferByteArray
import java.util.concurrent.LinkedBlockingQueue

class Injection {
    companion object {
        private val logger = Logger()
        fun provideLogger() = logger
        fun provideLinkedBlockingQueue() = LinkedBlockingQueue<TransferByteArray>()
    }
}