package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Contest;
import play.Play;
import play.db.jpa.*;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import javax.persistence.Persistence;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Created by JuanDa on 18/09/2015.
 */
public class ContestController extends Controller {

    @Transactional(value = "videoContest")
    public Result crearConcurso() {
        JsonNode json = request().body().asJson();
        Contest cont = Json.fromJson(json, Contest.class);
        if(cont.getId()==0){
            cont.save();
        }else{
            cont.update();
        }

        return ok(Json.toJson(cont));

    }


    @Transactional(value = "videoContest")
    public  Result listaConcursos(String  idUser) {
        List<Contest> contests = Contest.findByColumn2(Contest.class, "user", idUser);
        return ok(Json.toJson(contests));
    }



    @Transactional(value = "videoContest")
    public  Result getContest(Long id) {
        Contest cont = (Contest) Contest.findById(Contest.class,id);
        return ok(Json.toJson(cont));
    }

    @Transactional(value = "videoContest")
    public Result eliminarConcurso() {
        JsonNode json = request().body().asJson();
        Contest cont = Json.fromJson(json, Contest.class);
       cont.delete();
        return ok("ok");

    }

    @Transactional(value = "videoContest")
    public  Result concursoUnico(String  urlConcurso) {
        List<Contest> contests = Contest.findByColumn2(Contest.class, "url", urlConcurso);
        return redirect("http://localhost:9000/assets/concursos/" + urlConcurso);
        //return ok(Json.toJson(contests));
    }

    public Result subirVideo(){
        Http.MultipartFormData body = request().body().asMultipartFormData();
        Http.MultipartFormData.FilePart picture = body.getFile("file");
        if (picture != null) {
            File tempimg = picture.getFile();
            Path temp = tempimg.toPath();
            Path newFile = new File(Play.application().path().getAbsolutePath()+"/public/uploaded",picture.getFilename()).toPath();
            try {
                Files.move(temp, newFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return ok("File uploaded");
        } else {
            flash("error", "Missing file");
            return badRequest("File Missing");
        }

    }
}
