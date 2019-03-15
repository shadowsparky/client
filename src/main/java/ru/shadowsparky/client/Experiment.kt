package ru.shadowsparky.client

import org.bytedeco.javacpp.BytePointer
import org.bytedeco.javacpp.avcodec
import org.bytedeco.javacpp.avcodec.*
import org.bytedeco.javacpp.avutil
import org.bytedeco.javacpp.opencv_core
import org.bytedeco.javacpp.opencv_core.CV_8UC1
import org.bytedeco.javacpp.opencv_highgui.imshow
import java.nio.ByteBuffer
import java.util.*
import org.bytedeco.javacpp.PointerPointer
import org.bytedeco.javacpp.avutil.av_frame_alloc
import org.bytedeco.javacpp.opencv_core.CV_8UC3
import org.jcodec.codecs.h264.H264Decoder
import org.jcodec.common.JCodecUtil
import org.jcodec.common.model.ColorSpace
import org.jcodec.common.model.Picture
import org.jcodec.scale.AWTUtil
import java.awt.image.BufferedImage


class Experiment {
    private val log = Injection.provideLogger()
    private var codec = avcodec_find_decoder(AV_CODEC_ID_H264)
    private var c = AVCodecContext()
    private var picture = av_frame_alloc()
    private var packet = av_packet_alloc()
    private var TAG = javaClass.name

    init {
        c = avcodec_alloc_context3(codec)
        avcodec_open2(c, codec, avutil.AVDictionary())
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
        val mat = opencv_core.Mat(c.height(), c.width(), CV_8UC1, picture.data(0), picture.linesize(0).toLong())
//        Mat mat(c.height(), c.width(), CV_8UC3, picture.data[0], picture.linesize[0]);
        imshow("frame", mat);
        log.printInfo("Success")
    }
}