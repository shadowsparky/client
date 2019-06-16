/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.mvvm.views

import org.bytedeco.javacv.CanvasFrame
import org.bytedeco.javacv.Frame
import org.bytedeco.javacv.OpenCVFrameConverter
import org.opencv.core.Mat
import ru.shadowsparky.client.ConnectionType
import ru.shadowsparky.client.Logger
import ru.shadowsparky.client.interfaces.handlers.OrientationHandler
import ru.shadowsparky.client.mvvm.viewmodels.VideoViewModel
import ru.shadowsparky.client.objects.Injection
import ru.shadowsparky.client.projection.ProjectionWorker
import tornadofx.FX
import tornadofx.get
import java.awt.Toolkit
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import java.awt.event.MouseEvent
import java.awt.event.MouseListener

/**
 * Окно с проецированием
 *
 * @param projection подробнее: [ProjectionWorker]
 * @param title наименование окна приложения
 * @param type тип соединения
 * @property viewModel подробнее: [VideoViewModel]
 * @property log подробнее: [Logger]
 * @property converter позволяет преобразовывать в [Mat] из [Frame]
 */
class VideoView (
        private val projection: ProjectionWorker,
        title: String = FX.messages["projection"],
        type: ConnectionType = ConnectionType.wifi
) : CanvasFrame(title, 0, null, 1.0), OrientationHandler, MouseListener, KeyListener {
    private val viewModel: VideoViewModel
    private val converter = OpenCVFrameConverter.ToMat()
    private val log = Injection.provideLogger()

    init {
        this.isResizable = false
        this.canvas.isFocusable = true
        this.canvas.requestFocus()
        viewModel = Injection.provideVideoViewModel(this, type)
    }

    /**
     * Остановка проецирования
     */
    fun stopProjection() = projection.stop()

    /**
     * Установление размера Canvas'a с отображением мобильного экрана
     */
    override fun setCanvasSize(width: Int, height: Int) {
        val fixed = viewModel.getFixedSize(width, height)
        this.canvas.setSize(fixed.width, fixed.height)
        viewModel.updateInfelicity(width, height)
    }

    /**
     * Вывод [Mat] изображения
     */
    fun showImage(image: Mat) = super.showImage(converter.convert(image))


    /**
     * @see OrientationHandler
     */
    override fun onOrientationChanged(newWidth: Int, newHeight: Int) {
        this.setCanvasSize(newWidth, newHeight)
        val fdim = viewModel.getFixedSize(newWidth, newHeight)
        val dim = Toolkit.getDefaultToolkit().screenSize
        this.canvas.setLocation(dim.width / 2 - fdim.width / 2, 0)
    }

    /**
     * Обратный вызов нажатия кнопок
     */
    override fun keyPressed(e: KeyEvent?) = viewModel.onKeyPressed(e)

    /**
     * Обратный вызов кликов мыши
     */
    override fun mouseClicked(e: MouseEvent?) = viewModel.onMouseClicked(e)
    override fun mouseReleased(e: MouseEvent?){ /* nothing */ }
    override fun mouseEntered(e: MouseEvent?) { /* nothing */ }
    override fun mouseExited(e: MouseEvent?) { /* nothing */ }
    override fun mousePressed(e: MouseEvent?) { /* nothing */ }
    override fun keyTyped(e: KeyEvent?) { /* nothing */ }
    override fun keyReleased(e: KeyEvent?) { /* nothing */ }
}