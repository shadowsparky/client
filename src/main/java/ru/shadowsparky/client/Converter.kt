package ru.shadowsparky.client

import javafx.embed.swing.SwingFXUtils
import javafx.scene.image.Image
import org.bytedeco.javacpp.opencv_core
import org.opencv.core.Mat
import java.awt.image.BufferedImage
import java.awt.image.DataBufferByte
import org.bytedeco.javacv.Java2DFrameConverter
import org.bytedeco.javacv.Java2DFrameUtils
import org.bytedeco.javacv.OpenCVFrameConverter
import org.opencv.core.MatOfByte
import org.opencv.imgcodecs.Imgcodecs
import java.io.ByteArrayInputStream
import javax.imageio.ImageIO

class Converter {
//    val converter1 = OpenCVFrameConverter.ToMat()
//    val converter2 = OpenCVFrameConverter.ToOrgOpenCvCoreMat()
    fun matToBufferedImage(frame: Mat): BufferedImage {
        var type = 0
        if (frame.channels() == 1) {
            type = BufferedImage.TYPE_BYTE_GRAY
        } else if (frame.channels() == 3) {
            type = BufferedImage.TYPE_3BYTE_BGR
        }
        val image = BufferedImage(frame.width(), frame.height(), type)
        val raster = image.raster
        val dataBuffer = raster.dataBuffer as DataBufferByte
        val data = dataBuffer.data
        frame.get(0, 0, data)
        return image
    }

    fun bufferedImageToMat(bi: BufferedImage): opencv_core.Mat? {
        val cv = OpenCVFrameConverter.ToMat()
        return cv.convertToMat(Java2DFrameConverter().convert(bi))
    }

    fun Mat2Image(matrix: Mat): Image {
        val mob = MatOfByte()
        Imgcodecs.imencode(".jpg", matrix, mob)
        val ba = mob.toArray()
        val capture = ImageIO.read(ByteArrayInputStream(ba))
        val image = SwingFXUtils.toFXImage(capture, null)
        return image;
    }

    fun Mat2Image(matrix: opencv_core.Mat) : Image{
        val capture = Java2DFrameUtils.toBufferedImage(matrix)
        return SwingFXUtils.toFXImage(capture, null)
    }
}