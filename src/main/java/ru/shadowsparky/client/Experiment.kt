package ru.shadowsparky.client

import org.bytedeco.javacpp.*
import org.bytedeco.javacpp.avcodec.*
import org.bytedeco.javacpp.avutil.*
import org.bytedeco.javacpp.opencv_core.CV_8UC1
import org.bytedeco.javacpp.opencv_core.CV_8UC3
import org.bytedeco.javacpp.opencv_highgui.imshow
import org.bytedeco.javacpp.swscale.SWS_BICUBIC
import java.io.DataOutputStream
import java.nio.ByteBuffer
import java.nio.DoubleBuffer


class Experiment(val callback: ImageCallback) {
    private val log = Injection.provideLogger()
    private var codec = avcodec_find_decoder(AV_CODEC_ID_H264)
    private var c = AVCodecContext()
    private val width = 1280
    private val height = 720
    private var picture = av_frame_alloc()
    private var RGBPicture = av_frame_alloc()
    private var packet = av_packet_alloc()
    private var process: Process? = null
    private var TAG = javaClass.name
    private var mOut: DataOutputStream? = null
    private var converter = Injection.provideConverter()
    private var bytes: Int
    private var buffer: BytePointer
    private var convert_ctx: swscale.SwsContext? = null


    init {
        c = avcodec_alloc_context3(codec)
        avcodec_open2(c, codec, avutil.AVDictionary())
        bytes = av_image_get_buffer_size(AV_PIX_FMT_RGB24, width, height, 1)
        buffer = BytePointer(avutil.av_malloc(bytes.toLong()))
        av_image_fill_arrays(RGBPicture.data(), RGBPicture.linesize(), buffer, AV_PIX_FMT_RGB24, width, height, 1)
    }

    fun createProcess() {
        process = ProcessBuilder()
                .command("ffplay", "-framerate", "60", "-window_title", "PIPE OUT", "-")
                .start()
        mOut = DataOutputStream(process!!.outputStream)
    }

    fun writeToPipe(data: ByteArray) {
        mOut!!.write(data)
    }


    fun decode(data: ByteArray) {
        packet.data(BytePointer(ByteBuffer.wrap(data)))
        packet.size(data.size)
        log.printInfo(data.size.toString())
        var len = 0
        len = avcodec_send_packet(c, packet)
        if (len != 0) {
            log.printInfo("avcodec send packet error")
            return
        }
        len = avcodec_receive_frame(c, picture)
        if (len != 0) {
            log.printInfo("avcodec receive frame error")
            return
        }
        convert_ctx = swscale.sws_getContext(
                width,
                height,
                c.pix_fmt(),
                width,
                height,
                AV_PIX_FMT_BGR24,
                SWS_BICUBIC,
                null,
                null,
                DoublePointer())
        swscale.sws_scale(convert_ctx, picture.data(), picture.linesize(), 0, height, RGBPicture.data(), RGBPicture.linesize())
        val mats = opencv_core.Mat(height, width, CV_8UC3, RGBPicture.data(0), RGBPicture.linesize(0).toLong())
        val image = converter.Mat2Image(mats)
        callback.handleImage(image)
        log.printInfo("Success")
    }
}