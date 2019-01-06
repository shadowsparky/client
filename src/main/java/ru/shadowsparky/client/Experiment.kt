package ru.shadowsparky.client

import org.bytedeco.javacpp.*
import org.bytedeco.javacv.FFmpegFrameRecorder
import org.bytedeco.javacv.Frame
import java.io.File
import java.nio.ByteBuffer
import java.nio.file.Files
import java.util.*




class Experiment {
    private val log = Injection.provideLogger()
    private val FRAME_RATE = 60
    private val VIDEO_PATH = "/home/eugene/Downloads/videdo.mp4"
    private val IMAGES_PATH = "/home/eugene/Downloads/images/image.png"
    private val recorder = FFmpegFrameRecorder.createDefault(File(VIDEO_PATH), 720, 1280)
    private var frame = Frame(720, 1280, Frame.DEPTH_BYTE, 2)
//    private val context = avformat.AVFormatContext()

    fun mp4ToByteBuffer(bytes: ByteArray) : ByteBuffer {
        log.printInfo("$bytes size:${bytes.size}")
        return ByteBuffer.wrap(bytes)
    }

    fun startRecord() {
        recorder.frameNumber = 60
        recorder.frameRate = (60).toDouble()
        recorder.videoCodec = avcodec.AV_CODEC_ID_H264
        recorder.start()
    }

    fun stopRecord() {
        recorder.stop()
    }

    private fun pushFrame(frame: Frame) {
        try {
            recorder.record(frame)
        } catch (e: Exception) {
            log.printError("${e.printStackTrace()}")
        }
    }

    fun decodeFromVideo(data: ByteArray) {
        frame.image[0] = ByteBuffer.wrap(data)
        val buffer = (frame.image[0].position(0) as ByteBuffer)
//        try {
            buffer.clear()
            buffer.put(data)
//        } catch (e: BufferOverflowException) {
//            log.printError("${e.printStackTrace()}")
//            Log.i(TAG, "recordError BufferOverflowException $e")
//            frame = Frame(720, 1280, Frame.DEPTH_BYTE, 2)
//            val frameToRecord = FFmpegFrameRecorder.FrameToRecord(timestamp, frame)
//            (frame.image[0].position(0) as ByteBuffer).put(data)
//        }

//        buffer.put(data)
//        ((frame.image[0].position(0)) as ByteBuffer).put(data)
        pushFrame(frame)
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

    @Deprecated("Нет результата")
    fun decodeFromVideo(data: ByteArray, timestamp: Long) : opencv_core.IplImage? {
        val receivedVideoPacket: avcodec.AVPacket = avcodec.AVPacket()//? = null
        var returnImageFrame: opencv_core.IplImage? = null
        avcodec.av_init_packet(receivedVideoPacket)
        val frameFlag = data[1]
        var videoCodecContext: avcodec.AVCodecContext?
        val subData = Arrays.copyOfRange(data, 5, data.size)
        val videoData = BytePointer(*subData)
        if (frameFlag.toInt() == 0) {
            val codec = avcodec.avcodec_find_decoder(avcodec.AV_CODEC_ID_H264)
            if (codec != null) {
                videoCodecContext = avcodec.avcodec_alloc_context3(codec)
                videoCodecContext.width(720)
                videoCodecContext.height(1280)
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
                return null
            }
        } else {
            log.printError("FRAME FLAG NOT NULL")
            return null
        }
        val decodedPicture: avutil.AVFrame = avutil.av_frame_alloc()
        val processedPicture: avutil.AVFrame = avutil.av_frame_alloc()
        if ((decodedPicture != null))
            if (processedPicture != null) {
                val width = 720//videoCodecContext!!.width()
                val height = 1280//videoCodecContext.height()
                val fmt = 3
                val size = avcodec.avpicture_get_size(fmt, width, height)
                val proccessPictureBuffer = BytePointer(avutil.av_malloc(size.toLong()))
                avcodec.avpicture_fill(avcodec.AVPicture(processedPicture), proccessPictureBuffer, fmt, width, height)
                log.printInfo(processedPicture.toString())
                returnImageFrame = opencv_core.IplImage.createHeader(720, 1280, 8, 1)
//                returnImageFrame!!.imageData(videoData)
                receivedVideoPacket.data(videoData)
                receivedVideoPacket.size(videoData.capacity().toInt())
                receivedVideoPacket.pts(timestamp)
                videoCodecContext.pix_fmt(avutil.AV_PIX_FMT_YUV420P)
                var pointer = IntArray(10)
                val decodedFrameLength = avcodec.avcodec_receive_frame(videoCodecContext, decodedPicture)
                val sendPacket = avcodec.avcodec_send_packet(videoCodecContext, receivedVideoPacket)

//                val converter = OpenCVFrameConverter.ToIplImage()
//                returnImageFrame.imageData(decodedPicture.data(decodedPicture.pkt_size()))
//                cvShowImage("Original Content", returnImageFrame)
//                log.printInfo("$decodedFrameLength $sendPacket")
//                img_convert()
//                ("supreme original", decodedPicture)
//                if (decodedFrameLength >= 0) {
//                    log.printInfo("Will be okey...")
//                } else {
//                    log.printInfo("Will be not okey...")
//                }
            } else {
                log.printError("Processed Picture is null")
            }
        else {
            log.printError("Decoded picture is null")
        }
        return null
    }

    fun toByteArray() : ByteArray = Files.readAllBytes(File(VIDEO_PATH).toPath())

}