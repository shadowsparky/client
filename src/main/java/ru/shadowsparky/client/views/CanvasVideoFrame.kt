/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.views

import org.bytedeco.javacv.CanvasFrame
import org.scenicview.ScenicView
import java.awt.*
import javax.swing.JFrame
import org.bytedeco.librealsense.frame
import javax.swing.Spring.height
import javax.swing.Spring.width
import java.awt.Toolkit.getDefaultToolkit



class CanvasVideoFrame(title: String = "test") : /*CanvasFrame(title, 1.0)*/CanvasFrame(title, 0, null, 1.0) {

    init {
        this.isResizable = false
    }

    override fun showImage(image: Image) {
        super.showImage(image)
        this.location = Point(111, 1111111)

    }

    override fun setCanvasSize(width: Int, height: Int) {
        val fixed = getFixedSize(width, height)
        this.canvas.setSize(fixed.width, fixed.height)
        val dim = Toolkit.getDefaultToolkit().screenSize
        this.setLocation(dim.width / 2 - this.size.width / 2,
                dim.height / 2 - this.size.height / 2)
//        this.setLocation(1000, 1000)
//        super.setCanvasSize(fixed.width, fixed.height)
    }

    private fun getFixedSize(width: Int, height: Int) : Dimension {
        val screenSize = Toolkit.getDefaultToolkit().screenSize
        if (width < height) {
            val _height = (screenSize.height).toInt()
            screenSize.width = (_height * 0.6).toInt()
        }
        return screenSize
    }


}