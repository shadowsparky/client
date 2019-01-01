package ru.shadowsparky.client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.embed.swing.SwingFXUtils;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import java.nio.ByteBuffer;
import java.nio.ShortBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Dmitriy Gerashenko <d.a.gerashenko@gmail.com>
 */
public class TestMain extends Application {

    private static final Logger LOG = Logger.getLogger(TestMain.class.getName());

    private static volatile Thread playThread;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        StackPane root = new StackPane();
        ImageView imageView = new ImageView();

        root.getChildren().add(imageView);
        imageView.fitWidthProperty().bind(primaryStage.widthProperty());
        imageView.fitHeightProperty().bind(primaryStage.heightProperty());

        Scene scene = new Scene(root, 640, 480);

        primaryStage.setTitle("Video + audio");
        primaryStage.setScene(scene);
        primaryStage.show();

        playThread = new Thread(() -> {
            try {
//                String videoFilename = getParameters().getRaw().get(0);
                FFmpegFrameGrabber grabber = new FFmpegFrameGrabber("/home/eugene/Downloads/video.mp4");
                grabber.setFormat("mp4");
                grabber.setImageWidth(640);
                grabber.setImageHeight(480);
                grabber.start();
                primaryStage.setWidth(grabber.getImageWidth());
                primaryStage.setHeight(grabber.getImageHeight());
                AudioFormat audioFormat = new AudioFormat(grabber.getSampleRate(), 16, grabber.getAudioChannels(), true, true);

                DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
                SourceDataLine soundLine = (SourceDataLine) AudioSystem.getLine(info);
                soundLine.open(audioFormat);
                soundLine.start();

                Java2DFrameConverter converter = new Java2DFrameConverter();

                ExecutorService executor = Executors.newSingleThreadExecutor();

                while (!Thread.interrupted()) {
                    Frame frame = grabber.grab();
                    if (frame == null) {
                        break;
                    }
                    if (frame.image != null) {
                        Image image = SwingFXUtils.toFXImage(converter.convert(frame), null);
                        Platform.runLater(() -> {
                            imageView.setImage(image);
                        });
                    } else if (frame.samples != null) {
                        ShortBuffer channelSamplesShortBuffer = (ShortBuffer) frame.samples[0];
                        channelSamplesShortBuffer.rewind();

                        ByteBuffer outBuffer = ByteBuffer.allocate(channelSamplesShortBuffer.capacity() * 2);

                        for (int i = 0; i < channelSamplesShortBuffer.capacity(); i++) {
                            short val = channelSamplesShortBuffer.get(i);
                            outBuffer.putShort(val);
                        }

                        /**
                         * We need this because soundLine.write ignores
                         * interruptions during writing.
                         */
                        try {
                            executor.submit(() -> {
                                soundLine.write(outBuffer.array(), 0, outBuffer.capacity());
                                outBuffer.clear();
                            }).get();
                        } catch (InterruptedException interruptedException) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
                executor.shutdownNow();
                executor.awaitTermination(10, TimeUnit.SECONDS);
                soundLine.stop();
                grabber.stop();
                grabber.release();
                Platform.exit();
            } catch (Exception exception) {
                LOG.log(Level.SEVERE, null, exception);
                System.exit(1);
            }
        });
        playThread.start();
    }

    @Override
    public void stop() throws Exception {
        playThread.interrupt();
    }

}