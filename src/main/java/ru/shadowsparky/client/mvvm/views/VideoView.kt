/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.mvvm.views

import org.bytedeco.javacv.CanvasFrame
import org.bytedeco.javacv.OpenCVFrameConverter
import org.opencv.core.Mat
import ru.shadowsparky.client.projection.ProjectionWorker
import ru.shadowsparky.client.mvvm.viewmodels.VideoViewModel
import ru.shadowsparky.client.ConnectionType
import ru.shadowsparky.client.interfaces.handlers.OrientationHandler
import ru.shadowsparky.client.objects.Injection
import java.awt.Toolkit
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import java.awt.event.MouseEvent
import java.awt.event.MouseListener

class VideoView (
        private val projection: ProjectionWorker,
        title: String = "test",
        type: ConnectionType = ConnectionType.wifi
) : CanvasFrame(title, 0, null, 1.0), OrientationHandler, MouseListener, KeyListener {
    private val controller: VideoViewModel
    private val converter = OpenCVFrameConverter.ToMat()

    init {
        this.isResizable = false
        this.canvas.isFocusable = true
        this.canvas.requestFocus()
        controller = Injection.provideVideoController(this, type)
    }

    fun stopProjection() = projection.stop()

    override fun setCanvasSize(width: Int, height: Int) {
        val fixed = controller.getFixedSize(width, height)
        this.canvas.setSize(fixed.width, fixed.height)
        controller.updateIncfelicity(width, height)
    }

    fun showImage(image: Mat) = super.showImage(converter.convert(image))

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