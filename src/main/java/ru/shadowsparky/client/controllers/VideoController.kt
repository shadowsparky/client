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
import ru.shadowsparky.client.views.VideoView
import tornadofx.Controller
import java.awt.Dimension
import java.awt.Toolkit

class VideoController(private val view: VideoView, val type: ConnectionType) : Controller() {
    private var infelicity_width: Double = 0.0
    private var infelicity_height: Double = 0.0
    private val adb = Injection.provideAdb()
    private val _log = Injection.provideLogger()

    init {
        if (type == ConnectionType.adb) {
            enableADBActions()
        }
    }

    private fun enableADBActions() {
        setupMouse()
        setupKeyboard()
    }

    private fun setupKeyboard() {
        view.currentStage!!.scene.setOnKeyPressed {
            when(it.code) {
                KeyCode.UP ->  adb.invokeScrollUp()
                KeyCode.DOWN -> adb.invokeScrollDown()
                KeyCode.LEFT-> adb.invokeScrollLeft()
                KeyCode.RIGHT -> adb.invokeScrollRight()
                KeyCode.B, KeyCode.Z -> adb.invokeBackButton()
                KeyCode.R, KeyCode.C -> adb.invokeRecentApplicationsButton()
                KeyCode.H, KeyCode.X -> adb.invokeHomeButton()
                else -> _log.printInfo("KEY PRESSED")
            }
        }
    }


    private fun setupMouse() {
        view.image.addEventHandler(MouseEvent.MOUSE_CLICKED) {
            when(it.button) {
                MouseButton.PRIMARY -> {
                    val x = infelicity_width * it.x
                    val y = infelicity_height * it.y
                    val res = adb.tapToScreen(x, y)
                    if (res.status == ADBStatus.ERROR) {
                        _log.printError("TAP ERROR: ${res.info}")
                        return@addEventHandler
                    }
                    _log.printInfo("CoordX: (${x}) ${it.x} CoordY: (${y}) ${it.y}")
                }
            }
        }
    }

    fun updateIncfelicity(width: Double, height: Double) {
        infelicity_width = width / view.image.fitWidth
        infelicity_height = height / view.image.fitHeight
    }

    fun getScreenSize(image: Image) : Dimension {
        val screenSize = Toolkit.getDefaultToolkit().screenSize
        if (image.width < image.height) {
            val height = screenSize.height
            screenSize.width = (height * 0.6).toInt()
        }
        return screenSize
    }

}