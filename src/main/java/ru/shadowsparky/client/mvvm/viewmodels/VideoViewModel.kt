/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.mvvm.viewmodels

import ru.shadowsparky.client.ConnectionType
import ru.shadowsparky.client.objects.Injection
import ru.shadowsparky.client.adb.ADBStatus
import ru.shadowsparky.client.mvvm.views.VideoView
import ru.shadowsparky.client.interfaces.Controllerable
import ru.shadowsparky.client.mvvm.models.VideoModel
import tornadofx.Controller
import java.awt.Dimension
import java.awt.event.KeyEvent
import java.awt.event.KeyEvent.*

open class VideoViewModel(
        private val view: VideoView,
        val type: ConnectionType,
        private val model: VideoModel = Injection.provideVideoModel()
) : Controller(), Controllerable {
    private var infelicity_width: Double = 0.0
    private var infelicity_height: Double = 0.0
    private val _log = Injection.provideLogger()

    init {
        setupKeyboard()
        if (type == ConnectionType.adb) enableADBActions()
    }

    private fun enableADBActions() {
        setupMouse()
        _log.printInfo("Setup is done")
    }

    private fun setupKeyboard() = view.canvas.addKeyListener(view)

    fun onKeyPressed(event: KeyEvent?) {
        if (event == null) return
        if (type == ConnectionType.adb) {
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
            view.stopProjection()
        }
    }

    fun onMouseClicked(event: java.awt.event.MouseEvent?) {
        if (event == null) return
        when (event.button) {
            java.awt.event.MouseEvent.BUTTON1 -> {
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


    fun getFixedSize(width: Int, height: Int) : Dimension = model.getFixedSize(width, height)

    private fun setupMouse() = view.canvas.addMouseListener(view)

    fun updateIncfelicity(width: Int, height: Int) {
        infelicity_width = width.toDouble() / view.canvas.width.toDouble()
        infelicity_height = height.toDouble() / view.canvas.height.toDouble()
    }
}