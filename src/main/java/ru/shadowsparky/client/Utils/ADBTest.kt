package ru.shadowsparky.client.Utils

object ADBTest {
    fun executeCommand(commands: List<String>) {
        ProcessBuilder().command(commands).start()
    }
}