/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.mvvm.models

import ru.shadowsparky.client.objects.Injection
import java.awt.Dimension
import java.awt.Toolkit

open class VideoModel {
    private val adb = Injection.provideAdb()

    fun up() = adb.invokeScrollUp()
    fun down() = adb.invokeScrollDown()
    fun left() = adb.invokeScrollDown()
    fun right() = adb.invokeScrollRight()
    fun back() = adb.invokeBackButton()
    fun recent() = adb.invokeRecentApplicationsButton()
    fun home() = adb.invokeHomeButton()
    fun tap(x: Double, y: Double) = adb.tapToScreen(x, y)

    fun getFixedSize(width: Int, height: Int) : Dimension {
        val screenSize = Toolkit.getDefaultToolkit().screenSize
        if (width < height) {
            screenSize.width = (screenSize.height * 0.55).toInt()
        }
        return screenSize
    }
}