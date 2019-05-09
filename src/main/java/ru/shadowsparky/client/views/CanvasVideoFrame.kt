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
import ru.shadowsparky.client.utils.OrientationHandler
import ru.shadowsparky.client.utils.Resultable
import java.awt.Dimension
import java.awt.Toolkit
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import java.awt.event.MouseEvent
import java.awt.event.MouseListener

class CanvasVideoFrame(
        title: String = "test",
        handler: Resultable,
        addr: String,
        port: Int = Extras.PORT
) : CanvasFrame(title, 1.0), OrientationHandler, MouseListener, KeyListener {
    val client = Client(handler, this, addr, port)
    val controller: VideoController

    init {
        this.isResizable = false
        controller = if (addr == "127.0.0.1") {
            VideoController(this, ConnectionType.adb)
        } else {
            VideoController(this, ConnectionType.wifi)
        }
    }

    fun startProjection() {
        client.start()
    }

    override fun orientationChanged(newWidth: Int, newHeight: Int) {
        this.setSize(newWidth, newHeight)
    }

    private val converter = OpenCVFrameConverter.ToMat()

    fun showImage(image: Mat) {
        super.showImage(converter.convert(image))
    }

    override fun setSize(width: Int, height: Int) {
        val fixed = getFixedSize(width, height)
        super.setSize(fixed.width, fixed.height)
        this.canvas.setSize(fixed.width, fixed.height)
        controller.updateIncfelicity(width, height)
    }

    private fun getFixedSize(width: Int, height: Int) : Dimension {
        val screenSize = Toolkit.getDefaultToolkit().screenSize
        if (width < height) {
            val _height = (screenSize.height)
            screenSize.width = (_height * 0.6).toInt()
        }
        return screenSize
    }

    override fun keyPressed(e: KeyEvent?) = controller.onKeyPressed(e)
    override fun mouseClicked(e: MouseEvent?) = controller.onMouseClicked(e)
    override fun mouseReleased(e: MouseEvent?){}
    override fun mouseEntered(e: MouseEvent?) {}
    override fun mouseExited(e: MouseEvent?) {}
    override fun mousePressed(e: MouseEvent?) {}
    override fun keyTyped(e: KeyEvent?) {}
    override fun keyReleased(e: KeyEvent?) {}
}