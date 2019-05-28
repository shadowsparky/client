/*
 * Created by shadowsparky in 2019
 */

package ru.shadowsparky.client.adb

/**
 * Статус выполнения ADB Команды
 */
enum class ADBStatus {
    /**
     * Выполнение произошло без ошибок
     */
    OK,

    /**
     * Во время выполнения произошла ошибка
     */
    ERROR
}