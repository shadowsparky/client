/*
 * Created by shadowsparky in 2019
 */

@file:Suppress("UNREACHABLE_CODE")

package ru.shadowsparky.client.utils.adb

import ru.shadowsparky.client.utils.Extras
import ru.shadowsparky.client.utils.Extras.Companion.APP_SWITCH_BUTTON
import ru.shadowsparky.client.utils.Extras.Companion.BACK_BUTTON
import ru.shadowsparky.client.utils.Extras.Companion.HOME_BUTTON
import ru.shadowsparky.client.utils.Injection

class ADBWorker {
    private val log = Injection.provideLogger()
    private val executor = Injection.provideExecutor()

    fun forwardPort(device_id: String) : ADBResult {
        val result = executor.executeCommand(listOf("adb", "-s", device_id, "forward", "tcp:${Extras.FORWARD_PORT}", "tcp:${Extras.PORT}"))
        return baseEmptyChecking(result)
    }

    fun tapToScreen(x: Double, y: Double) : ADBResult {
        val result = executor.executeCommand(listOf("adb", "shell", "input", "tap", "$x", "$y"))
        return baseEmptyChecking(result)
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
        val result = executor.executeCommand(listOf("adb", "shell", "input", "keyevent", keycode))
        return baseEmptyChecking(result)
    }

    fun invokeScrollDown() : ADBResult {                                                    // x1   //y1  // x2   //y2  // time
        val result = executor.executeCommand(listOf("adb", "shell", "input", "swipe", "100", "600", "100", "300", "100"))
        return baseEmptyChecking(result)
    }

    fun invokeScrollUp() : ADBResult {
        val result = executor.executeCommand(listOf("adb", "shell", "input", "swipe", "100", "300", "100", "600", "100"))
        return baseEmptyChecking(result)
    }

    fun invokeScrollLeft() : ADBResult {
        val result = executor.executeCommand(listOf("adb", "shell", "input", "swipe", "300", "100", "1000", "100", "100"))
        return baseEmptyChecking(result)
    }

    fun invokeScrollRight() : ADBResult {
        val result = executor.executeCommand(listOf("adb", "shell", "input", "swipe", "1000", "100", "300", "100", "100"))
        return baseEmptyChecking(result)
    }

    // TODO: Проверка на "битые" девайсы и существование adb
    fun getDevices() : ADBResult {
        val result = executor.executeCommand(listOf("adb", "devices", "-l"))
        return baseNotEmptyChecking(result)
    }
}