/*
 * Created by shadowsparky in 2019
 */

@file:Suppress("UNREACHABLE_CODE")

package ru.shadowsparky.client.adb

import ru.shadowsparky.client.objects.Constants
import ru.shadowsparky.client.objects.Constants.APP_SWITCH_BUTTON
import ru.shadowsparky.client.objects.Constants.BACK_BUTTON
import ru.shadowsparky.client.objects.Constants.HOME_BUTTON
import ru.shadowsparky.client.objects.Injection
import ru.shadowsparky.client.exceptions.ConsoleExecutorException

open class ADBWorker {
    private val log = Injection.provideLogger()
    private val executor = Injection.provideExecutor()

    fun forwardPort(device_id: String) : ADBResult {
        return try {
            val result = executor.executeCommand(listOf("adb", "-s", device_id, "forward", "tcp:${Constants.FORWARD_PORT}", "tcp:${Constants.PORT}"))
            baseEmptyChecking(result)
        } catch (e: ConsoleExecutorException) {
            ADBResult(ADBStatus.ERROR, "${e.message}")
        }
    }

    fun tapToScreen(x: Double, y: Double) : ADBResult {
        return try {
            val result = executor.executeCommand(listOf("adb", "shell", "input", "tap", "$x", "$y"))
            baseEmptyChecking(result)
        } catch (e: ConsoleExecutorException) {
            ADBResult(ADBStatus.ERROR, "${e.message}")
        }
    }

    private fun baseEmptyChecking(result: String) : ADBResult {
        if (result.isEmpty())
            return ADBResult(ADBStatus.OK)
        return ADBResult(ADBStatus.ERROR, result)
    }

    private fun baseNotEmptyChecking(result: String) : ADBResult {
        if (result.isNotEmpty())
            return ADBResult(ADBStatus.OK, result)
        return ADBResult(ADBStatus.ERROR)
    }

    fun invokeHomeButton() : ADBResult = baseInvokeKeyEvent(HOME_BUTTON)
    fun invokeBackButton() : ADBResult = baseInvokeKeyEvent(BACK_BUTTON)
    fun invokeRecentApplicationsButton() : ADBResult = baseInvokeKeyEvent(APP_SWITCH_BUTTON)

    private fun baseInvokeKeyEvent(keycode: String) : ADBResult {
        return try {
            val result = executor.executeCommand(listOf("adb", "shell", "input", "keyevent", keycode))
            baseEmptyChecking(result)
        } catch (e: ConsoleExecutorException) {
            ADBResult(ADBStatus.ERROR, "${e.message}")
        }
    }

    fun invokeScrollDown() : ADBResult {
        return try {
            val result = executor.executeCommand(listOf("adb", "shell", "input", "swipe", "100", "600", "100", "300", "100"))
            baseEmptyChecking(result)
        } catch (e: ConsoleExecutorException) {
            ADBResult(ADBStatus.ERROR, "${e.message}")
        }
    }

    fun invokeScrollUp() : ADBResult {
        return try {
            val result = executor.executeCommand(listOf("adb", "shell", "input", "swipe", "100", "300", "100", "600", "100"))
            baseEmptyChecking(result)
        } catch (e: ConsoleExecutorException) {
            ADBResult(ADBStatus.ERROR, "${e.message}")
        }
    }

    fun invokeScrollLeft() : ADBResult {
        return try {
            val result = executor.executeCommand(listOf("adb", "shell", "input", "swipe", "300", "100", "1000", "100", "100"))
            baseEmptyChecking(result)
        } catch (e: ConsoleExecutorException) {
            ADBResult(ADBStatus.ERROR, "${e.message}")
        }
    }

    fun invokeScrollRight() : ADBResult {
        return try {
            val result = executor.executeCommand(listOf("adb", "shell", "input", "swipe", "1000", "100", "300", "100", "100"))
            baseEmptyChecking(result)
        } catch (e: ConsoleExecutorException) {
            ADBResult(ADBStatus.ERROR, "${e.message}")
        }
    }

    fun getDevices() : ADBResult {
        return try {
            val result = executor.executeCommand(listOf("adb", "devices", "-l"))
            baseNotEmptyChecking(result)
        } catch(e: ConsoleExecutorException) {
            ADBResult(ADBStatus.ERROR, "${e.message}")
        }

    }
}