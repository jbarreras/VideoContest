package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import javafx.scene.text.FontPosture;
import models.User;
import play.db.jpa.*;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

import javax.persistence.Persistence;

import java.util.List;

/**
 * Created by JuanDa on 18/09/2015.
 */
public class UserController extends Controller {

    @Transactional(value = "videoContest")
    public Result crearUsuario() {
        JsonNode json = request().body().asJson();
        User u = Json.fromJson(json, User.class);
        List<User> usuarios =User.findByColumn2(User.class,"email",u.getEmail());
        if (usuarios.size()>0){
            return ok("El usuario ya existe.");
        }else{
            u.save();
            return ok(Json.toJson(u));
        }
    }



    @Transactional(value = "videoContest")
    public  Result login(String correo, String clave) {
        Object entity = null;
        try {
            entity =  JPA.em().createQuery("FROM models.User WHERE email= '" + correo + "' and password='" + clave + "'").getSingleResult();
            return ok(Json.toJson(entity));
        } catch (Exception e) {
           //e.printStackTrace();
        }
        return ok(Json.toJson("error"));
    }


    @Transactional(value = "videoContest")
    public  Result getUsers() {
        List<User> people = User.findAll(User.class);
        return ok(Json.toJson(people));
    }
}
