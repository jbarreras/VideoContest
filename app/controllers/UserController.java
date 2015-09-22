package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.User;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

/**
 * Created by JuanDa on 18/09/2015.
 */
public class UserController extends Controller {

    @Transactional()
    public Result crearUsuario() {
        JsonNode json = request().body().asJson();
        User u = Json.fromJson(json, User.class);
        List<User> usuarios = User.findByColumn2(User.class, "email", u.getEmail());
        if (usuarios.size() > 0) {
            return ok("El usuario ya existe.");
        } else {
            u.save();
            return ok(Json.toJson(u));
        }
    }

    @Transactional()
    public Result login(String correo, String clave) {
        Object entity = null;
        try {
            entity = JPA.em().createQuery("FROM models.User WHERE email= '" + correo + "' and password='" + clave + "'").getSingleResult();
            return ok(Json.toJson(entity));
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return ok(Json.toJson("error"));
    }

    @Transactional()
    public Result getUsers() {
        List<User> people = User.findAll(User.class);
        return ok(Json.toJson(people));
    }
}