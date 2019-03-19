/*
 * Created by shadowsparky in 2019
 */

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
import org.opencv.core.CvType
import org.opencv.core.MatOfByte
import org.opencv.imgcodecs.Imgcodecs
import java.io.ByteArrayInputStream
import javax.imageio.ImageIO

class Converter {

//    fun matToBufferedImage(frame: Mat): BufferedImage {
//        var type = 0
//        if (frame.channels() == 1) {
//            type = BufferedImage.TYPE_BYTE_GRAY
//        } else if (frame.channels() == 3) {
//            type = BufferedImage.TYPE_3BYTE_BGR
//        }
//        val image = BufferedImage(frame.width(), frame.height(), type)
//        val raster = image.raster
//        val dataBuffer = raster.dataBuffer as DataBufferByte
//        val data = dataBuffer.data
//        frame.get(0, 0, data)
//        return image
//    }

    fun javacvMat2OpenCvMat(mat: opencv_core.Mat) : Mat {
        val bi = opencvMat2Bi(mat)
        return bi2JavacvMat(bi)
    }

    fun openCvMat2JavaCvMat(mat: Mat) : opencv_core.Mat {
        val bi = javacvMat2Bi(mat)
        return bi2OpencvMat(bi)
    }

    fun bi2JavacvMat(bi: BufferedImage): Mat {
        val mat = Mat(bi.height, bi.width, CvType.CV_8UC1)
        val data = (bi.raster.dataBuffer as DataBufferByte).data
        mat.put(0, 0, data)
        return mat
    }

    fun bi2OpencvMat(bi: BufferedImage): opencv_core.Mat {
        val cv = OpenCVFrameConverter.ToMat()
        return cv.convertToMat(Java2DFrameConverter().convert(bi))
    }

    fun opencvMat2Bi(mat : opencv_core.Mat): BufferedImage = Java2DFrameUtils.toBufferedImage(mat)

    fun javacvMat2Bi(mat: Mat) : BufferedImage {
        val mob = MatOfByte()
        Imgcodecs.imencode(".jpg", mat, mob)
        val ba = mob.toArray()
        return ImageIO.read(ByteArrayInputStream(ba))
    }

    fun Mat2Image(matrix: Mat): Image {
        val capture = javacvMat2Bi(matrix)
        val image = SwingFXUtils.toFXImage(capture, null)
        return image;
    }

    fun Mat2Image(matrix: opencv_core.Mat) : Image{
        val capture = opencvMat2Bi(matrix)
        return SwingFXUtils.toFXImage(capture, null)
    }
}