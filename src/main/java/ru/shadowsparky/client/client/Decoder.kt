/*
 * Created by shadowsparky in 2019
 */

package ru.shadowsparky.client.client

import javafx.embed.swing.SwingFXUtils
import javafx.scene.image.Image
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.bytedeco.ffmpeg.avcodec.AVCodecContext
import org.bytedeco.ffmpeg.avutil.AVDictionary
import org.bytedeco.ffmpeg.global.avcodec.*
import org.bytedeco.ffmpeg.global.avutil
import org.bytedeco.ffmpeg.global.avutil.*
import org.bytedeco.ffmpeg.global.swscale
import org.bytedeco.ffmpeg.global.swscale.SWS_BICUBIC
import org.bytedeco.ffmpeg.swscale.SwsContext
import org.bytedeco.javacpp.BytePointer
import org.bytedeco.javacpp.DoublePointer
import org.bytedeco.javacv.CanvasFrame
import org.bytedeco.javacv.OpenCVFrameConverter
import org.bytedeco.opencv.global.opencv_core.CV_8UC3
import org.opencv.core.Mat
import org.opencv.core.MatOfByte
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.osgi.OpenCVNativeLoader
import ru.shadowsparky.client.utils.Injection
import java.awt.image.BufferedImage
import java.awt.image.DataBufferByte
import java.io.ByteArrayInputStream
import java.io.Closeable
import java.nio.ByteBuffer
import org.bytedeco.librealsense.frame
import org.opencv.core.CvType.channels
import ru.shadowsparky.client.views.CanvasVideoFrame
import kotlin.math.roundToInt


class Decoder : Closeable {
    private val log = Injection.provideLogger()
    private var codec = avcodec_find_decoder(AV_CODEC_ID_H264)
    private var c = AVCodecContext()
    private var picture = av_frame_alloc()
    private var RGBPicture = av_frame_alloc()
    private var packet = av_packet_alloc()
    private var bytes: Int? = null
    private val converter = OpenCVFrameConverter.ToMat()
    private var buffer: BytePointer? = null
    private var convert_ctx: SwsContext? = null
    private var saved_width: Int = 0
    private var saved_height: Int = 0
    private val canvas = CanvasVideoFrame("test")
    private var skipper = 0;

    init {
        OpenCVNativeLoader().init()
        c = avcodec_alloc_context3(codec)
        avcodec_open2(c, codec, AVDictionary())

    }

    fun intelliParams() {
        if ((saved_width != c.width()) and (saved_height != c.height())) {
            bytes = av_image_get_buffer_size(AV_PIX_FMT_RGB24, c.width(), c.height(), 1)
            buffer = BytePointer(avutil.av_malloc(bytes!!.toLong()))
            av_image_fill_arrays(RGBPicture.data(), RGBPicture.linesize(), buffer, AV_PIX_FMT_RGB24, c.width(), c.height(), 1)
            saved_width = c.width()
            saved_height = c.height()
            canvas.setCanvasSize(saved_width, saved_height)
            log.printInfo("Orientation changed!")
        }
    }

    suspend fun decode(data: ByteArray, size: Int) : Image? {
        packet.data(BytePointer(ByteBuffer.wrap(data)))
        packet.size(data.size)
        var len = avcodec_send_packet(c, packet)
        if (len != 0) {
            return null
        }
        len = avcodec_receive_frame(c, picture)
        if (len != 0) {
            return null
        }
        intelliParams()
        convert_ctx = withContext(Dispatchers.Default) {
            swscale.sws_getContext(
                    c.width(),
                    c.height(),
                    c.pix_fmt(),
                    c.width(),
                    c.height(),
                    AV_PIX_FMT_BGR24,
                    SWS_BICUBIC,
                    null,
                    null,
                    DoublePointer()
            )
        }
        swscale.sws_scale(convert_ctx, picture.data(), picture.linesize(),  0, c.height(), RGBPicture.data(), RGBPicture.linesize())
        val mats = Mat(c.height(), c.width(), CV_8UC3, RGBPicture.data(0).asByteBuffer())
        log.printInfo("$size")
        Thread {
            canvas.showImage(converter.convert(mats))
        }.start()
        return null
    }

    override fun close() {
        GlobalScope.launch {
            codec.close()
            c.close()
            picture.close()
            RGBPicture.close()
            packet.close()
            buffer?.close()
            convert_ctx?.close()
        }
    }
}

fun Mat.toImage2() : Image {
    val buffer = MatOfByte()
    Imgcodecs.imencode(".bmp", this, buffer);
    return Image(ByteArrayInputStream(buffer.toArray()))
}

fun Mat.toImage() : Image {
    var type = BufferedImage.TYPE_BYTE_GRAY
    if (this.channels() > 1) {
        type = BufferedImage.TYPE_3BYTE_BGR
    }
    val bufferSize = this.channels() * this.cols() * this.rows()
    val b = ByteArray(bufferSize)
    this.get(0, 0, b)
    val image = BufferedImage(this.cols(), this.rows(), type)
    val targetPixels = (image.raster.dataBuffer as DataBufferByte).data
    System.arraycopy(b, 0, targetPixels, 0, b.size)
    return SwingFXUtils.toFXImage(image, null)
//    val buffer = MatOfByte()
//    Imgcodecs.imencode(".bmp", frame, buffer)
//    val image = Image(ByteArrayInputStream(buffer.toArray()));
//    return image
}
