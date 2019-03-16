package ru.shadowsparky.client

import org.bytedeco.javacpp.BytePointer
import org.bytedeco.javacpp.avcodec.*
import org.bytedeco.javacpp.avutil
import org.bytedeco.javacpp.avutil.av_frame_alloc
import org.bytedeco.javacpp.opencv_core
import org.bytedeco.javacpp.opencv_core.CV_8UC1
import org.bytedeco.javacpp.opencv_highgui.imshow
import java.nio.ByteBuffer


class Experiment(val callback: ImageCallback) {
    private val log = Injection.provideLogger()
    private var codec = avcodec_find_decoder(AV_CODEC_ID_H264)
    private var c = AVCodecContext()
    private var picture = av_frame_alloc()
    private var packet = av_packet_alloc()
    private var TAG = javaClass.name
    private var converter = Injection.provideConverter()

    init {
        c = avcodec_alloc_context3(codec)
        avcodec_open2(c, codec, avutil.AVDictionary())
//        c.width(720)
//        c.height(1280)
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


        val mats = opencv_core.Mat(c.height(), c.width(), CV_8UC1, picture.data(0), picture.linesize(0).toLong())
        val image = converter.Mat2Image(mats)
        callback.handleImage(image)
        imshow("debug", mats)
//        val mat = Mat(c.height(), c.width(), CV_8UC1)
//        Java2DFrameUtils.toBufferedImage(mats)
//        mat.get(0, 0, picture.data(0).asByteBuffer())
//        /val mat = converter2.convert(converter1.convert(mats));

//        val mats = Mat(c.height(), c.width(), CV_8UC1, picture.data(0).asByteBuffer())
//        cvarrToMat(mats)
//        val mat = Mat(mats.address())
//        val rgb = Mat()
//        val mat = converter.matToBufferedImage(mats)
//        Imgproc.cvtColor(mat, rgb, Imgproc.COLOR_BGR2RGB)
//        imshow("frame", mat);
//        imshow("frame", rgb);
//        val image = Mat2Image(mat)
//        callback.handleImage(image)
        log.printInfo("Success")
    }
}