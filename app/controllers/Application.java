package controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Contest;
import models.User;
import models.Video;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

public class Application extends Controller {

    @Transactional(value = "videoContest")
    public Result index() {

        User genrenteConcurso1 = new User();
        genrenteConcurso1.setName("genrenteConcurso");
        genrenteConcurso1.save();

        Contest concurso1 = new Contest();
        concurso1.setName("consurso1");
        concurso1.setUser(genrenteConcurso1);
        concurso1.save();

        User concursante1 = new User();
        concursante1.setName("concursante1");
        concursante1.save();

        /*Video videoConcursante1 = new Video();
        videoConcursante1.setUser(concursante1);
        concurso1.getVideos().add(videoConcursante1);
        concurso1.save();*/

        /*User concursante2 = new User();
        concursante2.setName("concursante2");
        Video videoConcursante2 = new Video();
        videoConcursante2.setUser(concursante2);
        concurso1.getVideos().add(videoConcursante2);

        concurso1.save();
*/
        return ok(index.render("Your new application is readii."));
    }

    public  Result sayHello() {
        ObjectNode result = Json.newObject();
        result.put("exampleField1", "foobar");
        result.put("exampleField2", "Hello world!");

        return ok(result);
    }
}
