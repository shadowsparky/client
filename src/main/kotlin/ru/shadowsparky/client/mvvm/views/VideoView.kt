/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.mvvm.views

import org.bytedeco.javacv.OpenCVFrameConverter
import org.bytedeco.javacv.CanvasFrame
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

open class VideoView (
        private val projection: ProjectionWorker,
        title: String = "test",
        type: ConnectionType = ConnectionType.wifi
) : CanvasFrame(title, 0, null, 1.0), OrientationHandler, MouseListener, KeyListener {
    private val viewModel: VideoViewModel
    private val converter = OpenCVFrameConverter.ToMat()
    private val log = Injection.provideLogger()

    init {
        this.isResizable = false
        this.canvas.isFocusable = true
        this.canvas.requestFocus()
        viewModel = Injection.provideVideoController(this, type)
    }

    fun stopProjection() = projection.stop()

    override fun setCanvasSize(width: Int, height: Int) {
        val fixed = viewModel.getFixedSize(width, height)
        this.canvas.setSize(fixed.width, fixed.height)
        log.printInfo("test: ${fixed.width} ${fixed.height}")
        viewModel.updateIncfelicity(width, height)
    }

    fun showImage(image: Mat) = super.showImage(converter.convert(image))

    override fun onOrientationChanged(newWidth: Int, newHeight: Int) {
        this.setCanvasSize(newWidth, newHeight)
        val fdim = viewModel.getFixedSize(newWidth, newHeight)
        val dim = Toolkit.getDefaultToolkit().screenSize
        this.canvas.setLocation(dim.width / 2 - fdim.width / 2, 0)
    }

    override fun keyPressed(e: KeyEvent?) = viewModel.onKeyPressed(e)
    override fun mouseClicked(e: MouseEvent?) = viewModel.onMouseClicked(e)
    override fun mouseReleased(e: MouseEvent?){ /* nothing */ }
    override fun mouseEntered(e: MouseEvent?) { /* nothing */ }
    override fun mouseExited(e: MouseEvent?) { /* nothing */ }
    override fun mousePressed(e: MouseEvent?) { /* nothing */ }
    override fun keyTyped(e: KeyEvent?) { /* nothing */ }
    override fun keyReleased(e: KeyEvent?) { /* nothing */ }
}