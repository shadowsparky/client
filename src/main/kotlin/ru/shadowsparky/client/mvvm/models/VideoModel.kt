/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.mvvm.models

import ru.shadowsparky.client.adb.ADBWorker
import ru.shadowsparky.client.objects.Injection
import java.awt.Dimension
import java.awt.Toolkit

/**
 * Model из MVVM для работы с видео
 *
 * @property adb подробнее: [ADBWorker]
 */
open class VideoModel {
    private val adb = Injection.provideAdb()

    /**
     * @see [ADBWorker.invokeScrollUp]
     */
    fun up() = adb.invokeScrollUp()

    /**
     * @see [ADBWorker.invokeScrollDown]
     */
    fun down() = adb.invokeScrollDown()

    /**
     * @see [ADBWorker.invokeScrollLeft]
     */
    fun left() = adb.invokeScrollLeft()

    /**
     * @see [ADBWorker.invokeScrollRight]
     */
    fun right() = adb.invokeScrollRight()

    /**
     * @see [ADBWorker.invokeBackButton]
     */
    fun back() = adb.invokeBackButton()

    /**
     * @see [ADBWorker.invokeRecentApplicationsButton]
     */
    fun recent() = adb.invokeRecentApplicationsButton()

    /**
     * @see [ADBWorker.invokeHomeButton]
     */
    fun home() = adb.invokeHomeButton()

    /**
     * @see [ADBWorker.tapToScreen]
     */
    fun tap(x: Double, y: Double) = adb.tapToScreen(x, y)

    /**
     * Получение исправленной ширины и высоты устройства
     *
     * @param width полученная ширина
     * @param height полученная выстота
     * @return исправленные параметры отображения устройства
     */
    fun getFixedSize(width: Int, height: Int) : Dimension {
        val screenSize = Toolkit.getDefaultToolkit().screenSize
        if (width < height) {
            screenSize.width = (screenSize.height * 0.60).toInt()
        }
        return screenSize
    }
}