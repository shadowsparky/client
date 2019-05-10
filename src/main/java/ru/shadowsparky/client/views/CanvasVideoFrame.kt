/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.views

import org.bytedeco.javacv.CanvasFrame
import org.bytedeco.javacv.OpenCVFrameConverter
import org.opencv.core.Mat
import ru.shadowsparky.client.client.Client
import ru.shadowsparky.client.controllers.VideoController
import ru.shadowsparky.client.utils.ConnectionType
import ru.shadowsparky.client.utils.Extras
import ru.shadowsparky.client.utils.Extras.Companion.LOCALHOST
import ru.shadowsparky.client.utils.OrientationHandler
import ru.shadowsparky.client.utils.Resultable
import java.awt.Color
import java.awt.Toolkit
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import javax.swing.Spring.height
import javax.swing.Spring.width
import java.awt.Toolkit.getDefaultToolkit
import javax.swing.Spring.height
import javax.swing.Spring.width





// String title, int screenNumber, DisplayMode displayMode, double gamma

class CanvasVideoFrame(
        title: String = "test",
        handler: Resultable,
        addr: String,
        port: Int = Extras.PORT
) : /*CanvasFrame(title, 1.0)*/ CanvasFrame(title, 0, null, 1.0), OrientationHandler, MouseListener, KeyListener {
    val client = Client(handler, this, addr, port)
    private val controller: VideoController
    private val converter = OpenCVFrameConverter.ToMat()

    init {
        this.isResizable = false
        this.canvas.isFocusable = true
        this.canvas.requestFocus()
        val type = if (addr == LOCALHOST) {
            ConnectionType.adb
        } else {
            ConnectionType.wifi
        }
        controller = VideoController(this, type)
    }

    override fun setCanvasSize(width: Int, height: Int) {
        val fixed = controller.getFixedSize(width, height)
        this.canvas.setSize(fixed.width, fixed.height)
        controller.updateIncfelicity(width, height)
    }

    /*override fun setSize(width: Int, height: Int) {
        val fixed = controller.getFixedSize(width, height)
        super.setSize(fixed.width, fixed.height)
        this.canvas.setSize(fixed.width, fixed.height)
        controller.updateIncfelicity(width, height)
    }*/

    fun stopProjection() {
        client.stop()
    }

    fun startProjection() = client.start()
    fun showImage(image: Mat) {
        super.showImage(converter.convert(image))
    }

    override fun onOrientationChanged(newWidth: Int, newHeight: Int) {
        this.setCanvasSize(newWidth, newHeight)
        val fdim = controller.getFixedSize(newWidth, newHeight)
        val dim = Toolkit.getDefaultToolkit().screenSize
        this.canvas.setLocation(dim.width / 2 - fdim.width / 2, 0)
    }
    override fun keyPressed(e: KeyEvent?) = controller.onKeyPressed(e)
    override fun mouseClicked(e: MouseEvent?) = controller.onMouseClicked(e)
    override fun mouseReleased(e: MouseEvent?){ /* nothing */ }
    override fun mouseEntered(e: MouseEvent?) { /* nothing */ }
    override fun mouseExited(e: MouseEvent?) { /* nothing */ }
    override fun mousePressed(e: MouseEvent?) { /* nothing */ }
    override fun keyTyped(e: KeyEvent?) { /* nothing */ }
    override fun keyReleased(e: KeyEvent?) { /* nothing */ }
}