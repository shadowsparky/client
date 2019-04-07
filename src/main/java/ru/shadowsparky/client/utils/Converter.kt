/*
 * Created by shadowsparky in 2019
 */

package ru.shadowsparky.client.utils

import javafx.embed.swing.SwingFXUtils
import javafx.scene.image.Image
import org.bytedeco.javacpp.opencv_core
import org.bytedeco.javacv.Java2DFrameUtils
import java.awt.image.BufferedImage

class Converter {

    fun opencvMat2Bi(mat : opencv_core.Mat): BufferedImage = Java2DFrameUtils.toBufferedImage(mat)

    fun Mat2Image(matrix: opencv_core.Mat) : Image {
        val capture = opencvMat2Bi(matrix)
        return SwingFXUtils.toFXImage(capture, null)
    }
}