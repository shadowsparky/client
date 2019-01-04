package ru.shadowsparky.client

import java.util.concurrent.LinkedBlockingQueue

class Injection {
    companion object {
        private val logger = Logger()
        fun provideLogger() = logger
        fun provideLinkedBlockingQueue() = LinkedBlockingQueue<EncodedBuffer>()
    }
}