package ru.shadowsparky.client

import org.jcodec.codecs.h264.H264Decoder
import org.jcodec.common.model.ColorSpace
import org.jcodec.common.model.Picture
import org.jcodec.scale.AWTUtil
import java.awt.Point
import java.awt.Transparency
import java.awt.image.*
import java.io.File
import java.nio.ByteBuffer
import java.nio.file.Files
import javax.imageio.ImageIO
import java.awt.image.BufferedImage
import org.jcodec.api.FrameGrab




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

    fun ex1(buffer: ByteBuffer) {
        val decoder = H264Decoder()
        buffer.rewind()
        val outBuffer = Picture.create(1280, 720, ColorSpace.ANY)
        val pic = decoder.decodeFrame(buffer, outBuffer.data)
        val bufferedImage = AWTUtil.toBufferedImage(pic)
        log.printInfo("$bufferedImage")
    }

    fun ex2_sample() {
        log.printInfo("Ex 2 start...")
        val frameNumber = 42
        log.printInfo("Ex 2 frame number initialized")
        val picture = FrameGrab.getFrameFromFile(File(VIDEO_PATH), frameNumber)
        log.printInfo("picture handled")
        val bufferedImage = AWTUtil.toBufferedImage(picture)
        log.printInfo("buffered image handled")
        ImageIO.write(bufferedImage, "png", File(IMAGES_PATH))
        log.printInfo("image wrote")
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