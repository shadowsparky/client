/*
 * Created by shadowsparky in 2019
 */

@file:Suppress("UNREACHABLE_CODE")

package ru.shadowsparky.client.adb

import ru.shadowsparky.client.ConsoleExecutor
import ru.shadowsparky.client.Logger
import ru.shadowsparky.client.objects.Constants
import ru.shadowsparky.client.objects.Constants.APP_SWITCH_BUTTON
import ru.shadowsparky.client.objects.Constants.BACK_BUTTON
import ru.shadowsparky.client.objects.Constants.HOME_BUTTON
import ru.shadowsparky.client.objects.Injection
import ru.shadowsparky.client.exceptions.ConsoleExecutorException

/**
 * Обёртка для работы с ADB.
 * Здесь находятся все команды, необходимые для работы с ADB.
 *
 * @property log подробнее: [Logger]
 * @property executor подробнее: [ConsoleExecutor]
 */
open class ADBWorker {
    private val log = Injection.provideLogger()
    private val executor = Injection.provideExecutor()

    /**
     * Переопределение порта.
     * Необходимо для того, чтобы данные из девайса с адресом 192.168.31.228:1337
     * перенаправлялись ПК. Данные будут доступны по адресу 127.0.0.1:7331
     *
     * @param device_id адрес Android устройства
     * @return результат выполнения команды. Подробнее: [ADBResult]
     */
    fun forwardPort(device_id: String) : ADBResult {
        return try {
            val result = executor.executeCommand(listOf("adb", "-s", device_id, "forward", "tcp:${Constants.FORWARD_PORT}", "tcp:${Constants.PORT}"))
            baseEmptyChecking(result)
        } catch (e: ConsoleExecutorException) {
            ADBResult(ADBStatus.ERROR, "${e.message}")
        }
    }

    /**
     * Нажатие на экран устройства
     *
     * @param x - координата x
     * @param y - координата y
     * @return результат выполнения команды. Подробнее: [ADBResult]
     */
    fun tapToScreen(x: Double, y: Double) : ADBResult {
        return try {
            val result = executor.executeCommand(listOf("adb", "shell", "input", "tap", "$x", "$y"))
            baseEmptyChecking(result)
        } catch (e: ConsoleExecutorException) {
            ADBResult(ADBStatus.ERROR, "${e.message}")
        }
    }

    /**
     * Проверка возвращенного результата от [ConsoleExecutor.executeCommand].
     * Если результат пустой, то все прошло успешно
     *
     * @param result результат выполнения [ConsoleExecutor.executeCommand]
     * @return результат выполнения команды. Подробнее: [ADBResult]
     */
    private fun baseEmptyChecking(result: String) : ADBResult {
        if (result.isEmpty())
            return ADBResult(ADBStatus.OK)
        return ADBResult(ADBStatus.ERROR, result)
    }

    /**
     * Проверка возвращенного результата от [ConsoleExecutor.executeCommand].
     * Если результат пустой, то все прошло успешно
     *
     * @param result результат выполнения [ConsoleExecutor.executeCommand]
     * @return результат выполнения команды. Подробнее: [ADBResult]
     */
    private fun baseNotEmptyChecking(result: String) : ADBResult {
        if (result.isNotEmpty())
            return ADBResult(ADBStatus.OK, result)
        return ADBResult(ADBStatus.ERROR)
    }

    /**
     * Программное нажатие на аппаратную кнопку "Домой"
     *
     * @return результат выполнения команды. Подробнее: [ADBResult]
     */
    fun invokeHomeButton() : ADBResult = baseInvokeKeyEvent(HOME_BUTTON)

    /**
     * Программное нажатие на аппаратную кнопку "Назад"
     *
     * @return результат выполнения команды. Подробнее: [ADBResult]
     */
    fun invokeBackButton() : ADBResult = baseInvokeKeyEvent(BACK_BUTTON)

    /**
     * Программное нажатие на аппаратную кнопку "Последние приложения"
     *
     * @return результат выполнения команды. Подробнее: [ADBResult]
     */
    fun invokeRecentApplicationsButton() : ADBResult = baseInvokeKeyEvent(APP_SWITCH_BUTTON)

    /**
     * Метод, используемый для нажатий на аппаратные кнопки
     *
     * @param keycode идентификатор кнопки
     * @return результат выполнения команды. Подробнее: [ADBResult]
     */
    private fun baseInvokeKeyEvent(keycode: String) : ADBResult {
        return try {
            val result = executor.executeCommand(listOf("adb", "shell", "input", "keyevent", keycode))
            baseEmptyChecking(result)
        } catch (e: ConsoleExecutorException) {
            ADBResult(ADBStatus.ERROR, "${e.message}")
        }
    }

    /**
     * Команда скроллинга вниз
     *
     * @return результат выполнения команды. Подробнее: [ADBResult]
     */
    fun invokeScrollDown() : ADBResult {
        return try {
            val result = executor.executeCommand(listOf("adb", "shell", "input", "swipe", "100", "600", "100", "300", "100"))
            baseEmptyChecking(result)
        } catch (e: ConsoleExecutorException) {
            ADBResult(ADBStatus.ERROR, "${e.message}")
        }
    }

    /**
     * Команда скроллинга вверх
     *
     * @return результат выполнения команды. Подробнее: [ADBResult]
     */
    fun invokeScrollUp() : ADBResult {
        return try {
            val result = executor.executeCommand(listOf("adb", "shell", "input", "swipe", "100", "300", "100", "600", "100"))
            baseEmptyChecking(result)
        } catch (e: ConsoleExecutorException) {
            ADBResult(ADBStatus.ERROR, "${e.message}")
        }
    }

    /**
     * Команда скроллинга влево
     *
     * @return результат выполнения команды. Подробнее: [ADBResult]
     */
    fun invokeScrollLeft() : ADBResult {
        return try {
            val result = executor.executeCommand(listOf("adb", "shell", "input", "swipe", "300", "100", "1000", "100", "100"))
            baseEmptyChecking(result)
        } catch (e: ConsoleExecutorException) {
            ADBResult(ADBStatus.ERROR, "${e.message}")
        }
    }

    /**
     * Команда скроллинга вправо
     *
     * @return результат выполнения команды. Подробнее: [ADBResult]
     */
    fun invokeScrollRight() : ADBResult {
        return try {
            val result = executor.executeCommand(listOf("adb", "shell", "input", "swipe", "1000", "100", "300", "100", "100"))
            baseEmptyChecking(result)
        } catch (e: ConsoleExecutorException) {
            ADBResult(ADBStatus.ERROR, "${e.message}")
        }
    }

    /**
     * Проверка существования ADB устройств
     *
     * @return результат выполнения команды. Подробнее: [ADBResult]
     */
    fun checkDevices() : ADBResult {
        return try {
            val result = executor.executeCommand(listOf("adb", "devices", "-l"))
            baseNotEmptyChecking(result)
        } catch(e: ConsoleExecutorException) {
            ADBResult(ADBStatus.ERROR, "${e.message}")
        }
    }
}