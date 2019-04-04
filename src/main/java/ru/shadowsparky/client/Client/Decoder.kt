/*
 * Created by shadowsparky in 2019
 */

package ru.shadowsparky.client.Client

import org.bytedeco.javacpp.*
import org.bytedeco.javacpp.avcodec.*
import org.bytedeco.javacpp.avutil.*
import org.bytedeco.javacpp.opencv_core.CV_8UC1
import org.bytedeco.javacpp.opencv_core.CV_8UC3
import org.bytedeco.javacpp.swscale.SWS_BICUBIC
import ru.shadowsparky.client.Utils.ImageCallback
import ru.shadowsparky.client.Utils.Injection
import ru.shadowsparky.screencast.PreparingData
import java.io.DataOutputStream
import java.nio.ByteBuffer

class Decoder(
        private val callback: ImageCallback,
        private val pData: PreparingData
) {
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

    init {
        c = avcodec_alloc_context3(codec)
        avcodec_open2(c, codec, avutil.AVDictionary())
        bytes = av_image_get_buffer_size(AV_PIX_FMT_RGB24, pData.width, pData.height, 1)
        buffer = BytePointer(avutil.av_malloc(bytes!!.toLong()))
        av_image_fill_arrays(RGBPicture.data(), RGBPicture.linesize(), buffer, AV_PIX_FMT_RGB24, pData.width, pData.height, 1)
    }

    fun dispose() {
        codec.close()
        c.close()
        picture.close()
        RGBPicture.close()
        packet.close()
        buffer?.close()
        convert_ctx?.close()
    }

    @Deprecated("you will die")
    fun createProcess() {
        process = ProcessBuilder()
                .command("ffplay", "-framerate", "60", "-window_title", "PIPE OUT", "-")
                .start()
        mOut = DataOutputStream(process!!.outputStream)
    }

    @Deprecated("you will die")
    fun writeToPipe(data: ByteArray) {
        mOut!!.write(data)
    }


    @Synchronized fun decode(data: ByteArray) {
        packet.data(BytePointer(ByteBuffer.wrap(data)))
        packet.size(data.size)
        var len = avcodec_send_packet(c, packet)
        if (len != 0) {
            return
        }
        len = avcodec_receive_frame(c, picture)
        if (len != 0) {
            return
        }
        convert_ctx = swscale.sws_getContext(
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

        swscale.sws_scale(convert_ctx, picture.data(), picture.linesize(), 0, c.height(), RGBPicture.data(), RGBPicture.linesize())
        val mats = opencv_core.Mat(c.height(), c.width(), CV_8UC3, RGBPicture.data(0), RGBPicture.linesize(0).toLong())
        val image = converter.Mat2Image(mats)
        callback.handleImage(image)
    }
}