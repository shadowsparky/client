/*
 * Created by shadowsparky in 2019
 */

package ru.shadowsparky.client.client

import javafx.scene.image.Image
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.bytedeco.javacpp.*
import org.bytedeco.javacpp.avcodec.*
import org.bytedeco.javacpp.avutil.*
import org.bytedeco.javacpp.opencv_core.CV_8UC3
import org.bytedeco.javacpp.swscale.SWS_BICUBIC
import ru.shadowsparky.client.utils.Injection
import java.io.Closeable
import java.io.DataOutputStream
import java.nio.ByteBuffer

class Decoder(
//        private val callback: ImageCallback//,
//        private var pData: PreparingData
) : Closeable {

    override fun close() {
        codec.close()
        c.close()
        picture.close()
        RGBPicture.close()
        packet.close()
        buffer?.close()
        convert_ctx?.close()
    }

    private val log = Injection.provideLogger()
    private var codec = avcodec_find_decoder(AV_CODEC_ID_H264)
    private var c = AVCodecContext()
    private var picture = av_frame_alloc()
    private var RGBPicture = av_frame_alloc()
    private var packet = av_packet_alloc()
    private var process: Process? = null
    private var mOut: DataOutputStream? = null
    private var converter = Injection.provideConverter()
    private var bytes: Int? = null
    private var buffer: BytePointer? = null
    private var convert_ctx: swscale.SwsContext? = null
    private var saved_width: Int = 0
    private var saved_height: Int = 0

    init {
        c = avcodec_alloc_context3(codec)
        avcodec_open2(c, codec, avutil.AVDictionary())
    }

    fun intelliParams() {
        if ((saved_width != c.width()) and (saved_height != c.height())) {
            bytes = av_image_get_buffer_size(AV_PIX_FMT_RGB24, c.width(), c.height(), 1)
            buffer = BytePointer(avutil.av_malloc(bytes!!.toLong()))
            av_image_fill_arrays(RGBPicture.data(), RGBPicture.linesize(), buffer, AV_PIX_FMT_RGB24, c.width(), c.height(), 1)
            saved_width = c.width()
            saved_height = c.height()
            log.printInfo("Orientation changed!")
        }
    }

    suspend fun decode(data: ByteArray) : Image? {
        packet.data(BytePointer(ByteBuffer.wrap(data)))
        packet.size(data.size)
        var len = withContext(Dispatchers.Default) { avcodec_send_packet(c, packet) }
        if (len != 0) {
            return null
        }
        len = withContext(Dispatchers.Default) { avcodec_receive_frame(c, picture) }
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
                    DoublePointer())
        }

        swscale.sws_scale(convert_ctx, picture.data(), picture.linesize(), 0, c.height(), RGBPicture.data(), RGBPicture.linesize())
        val mats = withContext(Dispatchers.Default) { opencv_core.Mat(c.height(), c.width(), CV_8UC3, RGBPicture.data(0), RGBPicture.linesize(0).toLong()) }
        val image = withContext(Dispatchers.Default) { converter.Mat2Image(mats) }
        GlobalScope.launch(Dispatchers.IO) {
            c.deallocate()
            mats.release()
            packet.deallocate()
            picture.deallocate()
            RGBPicture.deallocate()
        }
        return image
//        callback.handleImage(image)
    }
}