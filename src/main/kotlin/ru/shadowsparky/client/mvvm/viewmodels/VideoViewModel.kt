/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.mvvm.viewmodels

import ru.shadowsparky.client.ConnectionType
import ru.shadowsparky.client.Logger
import ru.shadowsparky.client.objects.Injection
import ru.shadowsparky.client.adb.ADBStatus
import ru.shadowsparky.client.mvvm.views.VideoView
import ru.shadowsparky.client.interfaces.ViewModelable
import ru.shadowsparky.client.mvvm.models.VideoModel
import tornadofx.Controller
import tornadofx.ViewModel
import java.awt.Dimension
import java.awt.event.KeyEvent
import java.awt.event.KeyEvent.*
import java.awt.event.MouseEvent
import java.awt.event.MouseEvent.BUTTON1

/**
 * ViewModel из MVVM для работы с видео
 *
 * @param view подробнее: [VideoView]
 * @param type подробнее: [ConnectionType]
 * @param model подробнее: [VideoModel]
 * @property infelicity_width коэфициент исправления ширины
 * @property infelicity_height коэфициент исправления высоты
 * @property _log подробнее: [Logger]
 */
open class VideoViewModel(
        private val view: VideoView,
        val type: ConnectionType,
        private val model: VideoModel = Injection.provideVideoModel()
) : ViewModel() {
    private var infelicity_width: Double = 0.0
    private var infelicity_height: Double = 0.0
    private val _log = Injection.provideLogger()

    init {
        setupKeyboard()
        if (type == ConnectionType.adb) enableADBActions()
    }

    /**
     * Включение дополнительных функций (нажатия на экран и тд) для ADB
     */
    private fun enableADBActions() {
        setupMouse()
        _log.printInfo("Setup is done")
    }

    /**
     * Инициализация нажатий на кнопки
     */
    private fun setupKeyboard() = view.canvas.addKeyListener(view)

    /**
     * Обработчик кнопок
     */
    fun onKeyPressed(event: KeyEvent?) {
        if (event == null) return
        if (type == ConnectionType.adb) { // Если соединение по ADB
            when (event.keyCode) {
                VK_UP -> model.up()
                VK_DOWN -> model.down()
                VK_LEFT -> model.left()
                VK_RIGHT -> model.right()
                VK_B, VK_Z -> model.back()
                VK_R, VK_C -> model.recent()
                VK_H, VK_X -> model.home()
            }
        }
        if (event.keyCode == VK_ESCAPE) {
            view.stopProjection() // При нажатии на esc проецирование завершается
        }
    }

    /**
     * Обработчик нажатий на Canvas
     */
    fun onMouseClicked(event: MouseEvent?) {
        if (event == null) return
        when (event.button) {
            BUTTON1 -> {
                val x = infelicity_width * event.x
                val y = infelicity_height * event.y
                val res = model.tap(x, y)
                if (res.status == ADBStatus.ERROR) {
                    _log.printError("TAP ERROR: ${res.info}")
                    return
                }
                _log.printInfo("CoordX: (${x}) ${event.x} CoordY: (${y}) ${event.y}")
            }
        }
    }

    /**
     * @see VideoModel.getFixedSize
     */
    fun getFixedSize(width: Int, height: Int) : Dimension = model.getFixedSize(width, height)

    /**
     * Инициализация обработчика нажатий на Canvas
     */
    private fun setupMouse() = view.canvas.addMouseListener(view)

    /**
     * Обновление коэфициентов исправления
     */
    fun updateInfelicity(width: Int, height: Int) {
        infelicity_width = width.toDouble() / view.canvas.width.toDouble()
        infelicity_height = height.toDouble() / view.canvas.height.toDouble()
    }
}