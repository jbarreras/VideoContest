package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Contest;
import models.Video;
import play.Play;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import DTO.*;

/**
 * Created by JuanDa on 18/09/2015.
 */
public class ContestController extends Controller {

    @Transactional()
    public Result crearConcurso() {
        JsonNode json = request().body().asJson();
        Contest cont = Json.fromJson(json, Contest.class);
        if (cont.getId() == 0) {
            cont.save();
        } else {
            Contest cont2 = (Contest) Contest.findById(Contest.class, Long.valueOf(cont.getId()));
            cont.setVideos(cont2.getVideos());
            cont.update();
        }

        return ok(Json.toJson(ContestDTO.ConvertirDTO(cont)));
    }

    @Transactional()
    public Result listaConcursos(String idUser) {
        List<Contest> contests = Contest.findByColumn2(Contest.class, "user", idUser);
        List<ContestDTO> lista = new ArrayList<ContestDTO>();
        for (Contest entidad : contests) {
            lista.add(ContestDTO.ConvertirDTO(entidad));
        }
        return ok(Json.toJson(lista));
    }

    @Transactional()
    public Result getContest(Long id) {
        Contest cont = (Contest) Contest.findById(Contest.class, id);
        return ok(Json.toJson(ContestDTO.ConvertirDTO(cont)));
    }

    @Transactional()
    public Result eliminarConcurso() {
        JsonNode json = request().body().asJson();
        Contest cont = Json.fromJson(json, Contest.class);
        cont.delete();
        return ok("ok");
    }

    @Transactional()
    public Result concursoUnico(String urlConcurso) {
        List<Contest> contests = Contest.findByColumn2(Contest.class, "url", urlConcurso);
        if (contests.size()>0){
            return ok(Json.toJson(ContestDTO.ConvertirDTO(contests.get(0))));
        }else{
            return ok("No se encontro");
        }

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
            return ok(picture.getFilename().toString());
        } else {
            flash("error", "Missing file");
            return badRequest("File Missing");
        }

    }

    @Transactional()
    public Result crearVideo() {
        JsonNode json = request().body().asJson();
        Video vid = Json.fromJson(json, Video.class);
        vid.save();

        return ok(Json.toJson(vid));
    }

    @Transactional()
    public Result listaVideo(String idConcurso) {
        List<Video> videos = (List<Video>) JPA.em().createQuery("FROM models.Video WHERE contest= " + idConcurso + " order by uploadDate desc ").getResultList();
        List<VideoDTO> lista = new ArrayList<VideoDTO>();
        for (Video entidad : videos) {
            lista.add(VideoDTO.ConvertirDTO(entidad));
        }

        return ok(Json.toJson(lista));
    }

    @Transactional()
    public Result listaVideoProcesados(String idConcurso) {
        List<Video> videos = null;
        try {
            videos = (List<Video>) JPA.em().createQuery("FROM models.Video WHERE contest= " + idConcurso + " and state='COMPLETED'  order by uploadDate desc ").getResultList();
            List<VideoDTO> lista = new ArrayList<VideoDTO>();
            for (Video entidad : videos) {
                lista.add(VideoDTO.ConvertirDTO(entidad));
            }
            return ok(Json.toJson(lista));
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return ok(Json.toJson("error"));
    }
}