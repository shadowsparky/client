package ru.shadowsparky.client

import org.bytedeco.javacpp.*
import java.io.File
import java.nio.ByteBuffer
import java.nio.file.Files
import java.util.*


class Experiment {
    private val log = Injection.provideLogger()
    private val FRAME_RATE = 60
    private val VIDEO_PATH = "/home/eugene/Downloads/video.mp4"
    private val IMAGES_PATH = "/home/eugene/Downloads/images/image.png"

    fun mp4ToByteBuffer(bytes: ByteArray) : ByteBuffer {
        log.printInfo("$bytes size:${bytes.size}")
        return ByteBuffer.wrap(bytes)
    }

//    fun av_init_packet(packet: avcodec.AVPacket) {
//        packet.dts(AV_NOPTS_VALUE)
//        packet.pts(AV_NOPTS_VALUE)
//        packet.pos(-1)
//        packet.duration(0)
//        packet.convergence_duration(0)
//        packet.flags(0)
//        packet.stream_index(0)
//    }

    fun decodeFromVideo(data: ByteArray, timestamp: Long) : opencv_core.IplImage? {
        val receivedVideoPacket: avcodec.AVPacket = avcodec.AVPacket()//? = null
        var returnImageFrame: opencv_core.IplImage? = null
        avcodec.av_init_packet(receivedVideoPacket)
        val frameFlag = data[1]
        var videoCodecContext: avcodec.AVCodecContext? = null
        val subData = Arrays.copyOfRange(data, 5, data.size)
        val videoData = BytePointer(*subData)
        if (frameFlag.toInt() == 0) {
            val codec = avcodec.avcodec_find_decoder(avcodec.AV_CODEC_ID_H264)
            if (codec != null) {
                videoCodecContext = avcodec.avcodec_alloc_context3(codec)
                videoCodecContext.width(320)
                videoCodecContext.height(240)
                videoCodecContext.pix_fmt(avutil.AV_PIX_FMT_YUV420P)
                videoCodecContext.codec_type(avutil.AVMEDIA_TYPE_VIDEO)
                videoCodecContext.extradata(videoData)
                videoCodecContext.extradata_size(videoData.capacity().toInt())
                videoCodecContext.flags2(videoCodecContext.flags2() or avcodec.AV_CODEC_FLAG2_CHUNKS)
                avcodec.avcodec_open2(videoCodecContext, codec, null as PointerPointer<*>?)
                if ((videoCodecContext.time_base().num() > 1000) and (videoCodecContext.time_base().den() == 1)) {
                    videoCodecContext.time_base().den(1000)
                }
            } else {
                log.printError("CODEC IS NULL")
            }
        } else {
            log.printError("FRAME FLAG NOT NULL")
        }
        val decodedPicture: avutil.AVFrame = avutil.av_frame_alloc()
        val processedPicture: avutil.AVFrame = avutil.av_frame_alloc()
        if ((decodedPicture != null))
            if (processedPicture != null) {
                val width = videoCodecContext!!.width()
                val height = videoCodecContext.height()
                val fmt = 3
                val size = avcodec.avpicture_get_size(fmt, width, height)
                val proccessPictureBuffer = BytePointer(avutil.av_malloc(size.toLong()))
                avcodec.avpicture_fill(avcodec.AVPicture(processedPicture), proccessPictureBuffer, fmt, width, height)
                log.printInfo(processedPicture.toString())
                returnImageFrame = opencv_core.IplImage.createHeader(320, 240, 8, 1)
                receivedVideoPacket!!.data(videoData)
                receivedVideoPacket.size(videoData.capacity().toInt())
                receivedVideoPacket.pts(timestamp)
            } else {
                log.printError("Processed Picture is null")
            }
        else {
            log.printError("Decoded picture is null")
        }
        return returnImageFrame
    }

    fun toByteArray() : ByteArray = Files.readAllBytes(File(VIDEO_PATH).toPath())

}