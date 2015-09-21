package utilities.convert;

import models.Video;
import models.VideoState;
import net.bramp.ffmpeg.job.FFmpegJob;
import play.Logger;
import play.Play;
import play.db.jpa.JPA;
import utilities.message.Mail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExecuteProcess {

    public void run() {
        List<Video> pendingVideos = getPendingVideos();
        if (pendingVideos.size() > 0) {
            for (Video pendingVideo : pendingVideos) {
                try {
                    pendingVideo.setUrlConvertVideo(Play.application().configuration().getString("videoconverter.targetVideoFolder") + java.util.UUID.randomUUID().toString() + ".mp4");
                    pendingVideo.setStartDateConversion(new Date());
                    FFmpegConverter converter = new FFmpegConverter();
                    FFmpegJob.State completedStatus = converter.execute(pendingVideo.getUrlVideo(), pendingVideo.getUrlConvertVideo());
                    if (completedStatus == FFmpegJob.State.FINISHED) {
                        setCompletedVideo(pendingVideo);
                        sendMail(pendingVideo);
                    }
                    Logger.info("ExecuteProcess.run converter state " + completedStatus);
                } catch (Exception ex) {
                    Logger.error("ExecuteProcess.run " + ex.getMessage().toString());
                }
            }
        } else {
            Logger.info("ExecuteProcess.run video state no " + VideoState.PENDING);
        }
    }

    private void sendMail(Video completedVideo) {
        Mail.sendMail(completedVideo.getEmail(), "Le informamos que el video ya ha sido publicado en la página del concurso", "Su video ha sido publicado!");
    }

    private void setCompletedVideo(Video completedVideo) {
        JPA.withTransaction(() -> {
            completedVideo.setFinishConversion(new Date());
            completedVideo.setState(VideoState.COMPLETED);
            completedVideo.update();
        });
    }

    private List<Video> getPendingVideos() {
        List<Video> pendingVideos = new ArrayList<>();
        JPA.withTransaction(() -> {
            for (Video pendingVideo : (List<Video>) Video.findByColumn(Video.class, "state = '" + VideoState.PENDING + "'")) {
                pendingVideos.add(pendingVideo);
            }
        });
        return pendingVideos;
    }
}