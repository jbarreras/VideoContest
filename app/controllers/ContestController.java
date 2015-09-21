package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Contest;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

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
            cont.update();
        }

        return ok(Json.toJson(cont));
    }

    @Transactional()
    public Result listaConcursos(String idUser) {
        List<Contest> contests = Contest.findByColumn2(Contest.class, "user", idUser);
        return ok(Json.toJson(contests));
    }

    @Transactional()
    public Result getContest(Long id) {
        Contest cont = (Contest) Contest.findById(Contest.class, id);
        return ok(Json.toJson(cont));
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
        return redirect("http://localhost:9000/assets/concursos/" + urlConcurso);
        //return ok(Json.toJson(contests));
    }
}