package ru.shadowsparky.client

import javafx.embed.swing.SwingFXUtils
import javafx.scene.image.Image
import org.bytedeco.javacpp.opencv_core
import org.bytedeco.javacv.Frame
import org.bytedeco.javacv.Frame.DEPTH_BYTE
import org.bytedeco.javacv.OpenCVFrameConverter
import org.jcodec.api.FrameGrab
import org.jcodec.codecs.h264.H264Decoder
import org.jcodec.common.io.NIOUtils
import org.jcodec.common.model.ColorSpace
import org.jcodec.common.model.Picture
import org.jcodec.scale.AWTUtil
import org.opencv.core.Mat
import java.awt.Point
import java.awt.Transparency
import java.awt.image.*
import java.awt.image.BufferedImage
import java.io.File
import java.nio.ByteBuffer
import java.nio.file.Files
import javax.imageio.ImageIO


class Experiment {
    private val log = Injection.provideLogger()
    private val FRAME_RATE = 60
    private val VIDEO_PATH = "/home/eugene/Downloads/video.mp4"
    private val IMAGES_PATH = "/home/eugene/Downloads/images/image.png"
    fun mp4ToByteBuffer(bytes: ByteArray) : ByteBuffer {
//        val bytes = toByteArray()
        log.printInfo("$bytes size:${bytes.size}")
        return ByteBuffer.wrap(bytes)
    }

    fun toByteArray() : ByteArray = Files.readAllBytes(File(VIDEO_PATH).toPath())
    @Deprecated("CRASHED")
    fun exFrame(bytes: ByteArray): opencv_core.Mat? {
        log.printInfo("Frame experiment started")
        val frame = Frame(1280, 720, DEPTH_BYTE, 3)
        log.printInfo("Frame created")
        frame.image = arrayOf(mp4ToByteBuffer(bytes))
        log.printInfo("Frame image attached")
//        val converter = JavaFXFrameConverter()
        log.printInfo("Converter created")
        val converter = OpenCVFrameConverter.ToMat()
        return converter.convert(frame)
    }

    private fun mat2Image(frame: Mat): Image {
        var type = BufferedImage.TYPE_BYTE_GRAY
        if (frame.channels() > 1) {
            type = BufferedImage.TYPE_3BYTE_BGR
        }
        val bufferSize = frame.channels() * frame.cols() * frame.rows()
        val b = ByteArray(bufferSize)
        frame.get(0, 0, b) // get all the pixels
        val image = BufferedImage(frame.cols(), frame.rows(), type)
        val targetPixels = (image.raster.dataBuffer as DataBufferByte).data
        System.arraycopy(b, 0, targetPixels, 0, b.size)
        return SwingFXUtils.toFXImage(image, null)

    }


    @Deprecated("Пример не работает. Null pointer")
    fun ex1(buffer: ByteBuffer) {
        val decoder = H264Decoder()
        buffer.rewind()
        val outBuffer = Picture.create(1280, 720, ColorSpace.ANY)
        val pic = decoder.decodeFrame(buffer, outBuffer.data)
        val bufferedImage = AWTUtil.toBufferedImage(pic)
        log.printInfo("$bufferedImage")
    }
    @Deprecated("Пример не работает")
    fun ex2_sample() {
        log.printInfo("Ex 2 start...")
        val frameNumber = 42
        log.printInfo("Ex 2 frame number initialized")
        val picture = FrameGrab.getFrameAtSec(File(VIDEO_PATH), 0.0)
        log.printInfo("picture handled")
        val bufferedImage = AWTUtil.toBufferedImage(picture)
        log.printInfo("buffered image handled")
        ImageIO.write(bufferedImage, "png", File(IMAGES_PATH))
        log.printInfo("image wrote")
    }
    @Deprecated("Пример не работает")
    fun ex3_sample() {
        val file = File(VIDEO_PATH)
        log.printInfo("File handled")
        val grab = FrameGrab.createFrameGrab(NIOUtils.readableChannel(file))
        log.printInfo("Frame grab created")
        var picture: Picture
        picture = grab.nativeFrame
        while (true) {
            log.printInfo(picture.width.toString() + "x" + picture.height + " " + picture.color)
            picture = grab.nativeFrame
        }
    }

    fun mp4ToPng(byteArray: ByteArray) {
        val buffer = DataBufferByte(byteArray, byteArray.size)
        val width = 100
        val height = 100
        val raster = Raster.createInterleavedRaster(buffer, width, height, 3 * width, 3, intArrayOf(0, 1, 2), null as Point?)
        val cm = ComponentColorModel(ColorModel.getRGBdefault().colorSpace, false, true, Transparency.OPAQUE, DataBuffer.TYPE_BYTE)
        val image = BufferedImage(cm, raster, true, null)
        ImageIO.write(image, "png", File(IMAGES_PATH))
        log.printInfo("PNG saved on $IMAGES_PATH")
    }
}