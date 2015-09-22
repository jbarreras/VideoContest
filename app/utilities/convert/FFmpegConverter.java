package utilities.convert;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.job.FFmpegJob;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import play.Play;

import java.util.concurrent.*;

public class FFmpegConverter {

    private ExecutorService executor = Executors.newFixedThreadPool(10);
    //private ExecutorService executor = Executors.newSingleThreadExecutor();

    public FFmpegJob.State execute(String pendingVideo, String completedVideo) throws Exception {
        FFmpeg ffmpeg = new FFmpeg(Play.application().configuration().getString("videoconverter.FFmpeg"));
        FFprobe ffprobe = new FFprobe(Play.application().configuration().getString("videoconverter.FFprobe"));
        FFmpegProbeResult in = ffprobe.probe(pendingVideo);
        FFmpegBuilder builder = new FFmpegBuilder()
                .setInput(in)
                .overrideOutputFiles(true)
                .addOutput(completedVideo)
                .setFormat("mp4")
                .setVideoCodec("libx264")
                .setAudioCodec("libvo_aacenc")
                .setVideoFrameRate(FFmpeg.FPS_30)
                .setVideoResolution(320, 240)
                .setTargetSize(1024 * 1024)
                .done();
        FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
        FFmpegJob job = executor.createTwoPassJob(builder);
        runAndWait(job);
        return job.getState();
    }

    protected void runAndWait(FFmpegJob job) throws ExecutionException, InterruptedException {
        Future<?> future = executor.submit(job);
        while (!future.isDone()) {
            try {
                future.get(100, TimeUnit.MILLISECONDS);
                break;
            } catch (TimeoutException e) {
            }
        }
    }
}