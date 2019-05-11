/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.utils.projection

import org.bytedeco.ffmpeg.avcodec.AVCodecContext
import org.bytedeco.ffmpeg.avutil.AVDictionary
import org.bytedeco.ffmpeg.global.avcodec.*
import org.bytedeco.ffmpeg.global.avutil.*
import org.bytedeco.ffmpeg.global.swscale
import org.bytedeco.ffmpeg.global.swscale.SWS_BICUBIC
import org.bytedeco.ffmpeg.swscale.SwsContext
import org.bytedeco.javacpp.BytePointer
import org.bytedeco.javacpp.DoublePointer
import org.bytedeco.opencv.global.opencv_core.CV_8UC3
import org.opencv.core.Mat
import ru.shadowsparky.client.utils.interfaces.handlers.OrientationHandler
import ru.shadowsparky.client.utils.objects.Injection
import java.io.Closeable
import java.nio.ByteBuffer

class Decoder(val handler: OrientationHandler) : Closeable {
    private val log = Injection.provideLogger()
    private var codec = avcodec_find_decoder(AV_CODEC_ID_H264)
    private var c = AVCodecContext()
    private var picture = av_frame_alloc()
    private var RGBPicture = av_frame_alloc()
    private var packet = av_packet_alloc()
    private var bytes: Int? = null
    private var buffer: BytePointer? = null
    private var convert_ctx: SwsContext? = null
    private var saved_width: Int = 0
    private var saved_height: Int = 0

    init {
        c = avcodec_alloc_context3(codec)
        avcodec_open2(c, codec, AVDictionary())
    }

    private fun checkOrientation() {
        if ((saved_width != c.width()) and (saved_height != c.height())) {
            bytes = av_image_get_buffer_size(AV_PIX_FMT_RGB24, c.width(), c.height(), 1)
            buffer = BytePointer(av_malloc(bytes!!.toLong()))
            av_image_fill_arrays(RGBPicture.data(), RGBPicture.linesize(), buffer, AV_PIX_FMT_RGB24, c.width(), c.height(), 1)
            saved_width = c.width()
            saved_height = c.height()
            handler.onOrientationChanged(saved_width, saved_height)
            log.printInfo("Orientation changed!")
        }
    }

    private fun sendPacket() = avcodec_send_packet(c, packet) == 0
    private fun receiveFrame() = avcodec_receive_frame(c, picture) == 0

    private fun getConvertContext() : SwsContext {
        return swscale.sws_getContext(
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

    private fun fillRGBPicture() {
        swscale.sws_scale(
                convert_ctx,
                picture.data(),
                picture.linesize(),
                0,
                c.height(),
                RGBPicture.data(),
                RGBPicture.linesize()
        )
    }

    fun decode(data: ByteArray) : Mat? {
        packet.apply {
            data(BytePointer(ByteBuffer.wrap(data)))
            size(data.size)
        }
        if (!sendPacket()) return null
        if (!receiveFrame()) return null
        checkOrientation()
        convert_ctx = getConvertContext()
        fillRGBPicture()
        return Mat(c.height(), c.width(), CV_8UC3, RGBPicture.data(0).asByteBuffer())
    }

    override fun close() {
        codec.close()
        c.close()
        picture.close()
        RGBPicture.close()
        packet.close()
        buffer?.close()
        convert_ctx?.close()
    }
}