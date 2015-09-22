package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.User;
import net.bramp.ffmpeg.job.FFmpegJob;
import play.data.DynamicForm;
import play.data.Form;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import utilities.convert.FFmpegConverter;

import java.util.List;

/**
 * Created by Jorge on 21/09/2015.
 */
public class TestConvert extends Controller {

    @Transactional()
    public Result execute() {
        try {
            DynamicForm requestData = Form.form().bindFromRequest();
            FFmpegConverter converter = new FFmpegConverter();
            FFmpegJob.State completedStatus = converter.execute(requestData.get("sourceVideo"), requestData.get("targetVideo") + java.util.UUID.randomUUID().toString() + ".mp4");
            return ok();
        } catch (Exception ex) {
            return status(500);
        }
    }
}
