/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.controllers

import javafx.scene.image.Image
import javafx.scene.input.KeyCode
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent
import ru.shadowsparky.client.utils.ConnectionType
import ru.shadowsparky.client.utils.Injection
import ru.shadowsparky.client.utils.adb.ADBStatus
import ru.shadowsparky.client.views.CanvasVideoFrame
import ru.shadowsparky.client.views.VideoView
import tornadofx.Controller
import java.awt.Dimension
import java.awt.RenderingHints
import java.awt.Toolkit
import java.awt.event.KeyEvent
import java.awt.event.KeyEvent.*

class VideoController(private val view: CanvasVideoFrame, val type: ConnectionType) : Controller() {
    private var infelicity_width: Double = 0.0
    private var infelicity_height: Double = 0.0
    private val adb = Injection.provideAdb()
    private val _log = Injection.provideLogger()

    init {
        if (type == ConnectionType.adb)
            enableADBActions()
    }

    private fun enableADBActions() {
        setupMouse()
        setupKeyboard()
    }

    private fun setupKeyboard() {
        view.contentPane.addKeyListener(view)
    }

    fun onKeyPressed(event: KeyEvent?) {
        if (event == null) return
        when (event.keyCode) {
            VK_UP -> adb.invokeScrollUp()
            VK_DOWN -> adb.invokeScrollDown()
            VK_LEFT -> adb.invokeScrollLeft()
            VK_RIGHT -> adb.invokeScrollRight()
            VK_B, VK_Z -> adb.invokeBackButton()
            VK_R, VK_C -> adb.invokeRecentApplicationsButton()
            VK_H, VK_X -> adb.invokeHomeButton()
            else -> _log.printInfo("KEY PRESSED")
        }
    }

    fun onMouseClicked(event: java.awt.event.MouseEvent?) {
        if (event == null) return
        when (event.button) {
            java.awt.event.MouseEvent.BUTTON1 -> {
                val x = infelicity_width * event.x
                val y = infelicity_height * event.y
                val res = adb.tapToScreen(x, y)
                if (res.status == ADBStatus.ERROR) {
                    _log.printError("TAP ERROR: ${res.info}")
                    return
                }
                _log.printInfo("CoordX: (${x}) ${event.x} CoordY: (${y}) ${event.y}")
            }
        }
    }

    private fun setupMouse() = view.canvas.addMouseListener(view)

    fun updateIncfelicity(width: Int, height: Int) {
        infelicity_width = width.toDouble() / view.canvas.width.toDouble()
        infelicity_height = height.toDouble() / view.canvas.height.toDouble()
    }
}