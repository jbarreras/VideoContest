package utilities.schedule;

import akka.actor.Cancellable;
import play.Logger;
import play.Play;
import play.libs.Akka;
import scala.concurrent.duration.FiniteDuration;
import utilities.convert.ExecuteProcess;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ScheduleProcess {

    public static ScheduleProcess converter;
    private Cancellable sheduler;
    private Date initDate;
    private Date lastExecution;
    private Runnable execute = new Runnable() {
        @Override
        public void run() {
            lastExecution = new Date();
            ExecuteProcess executor = new ExecuteProcess();
            executor.run();
            Logger.info("ScheduleProcess.run " + lastExecution.toString());
        }
    };

    private ScheduleProcess() {
        try {
            initDate = new Date();
            Logger.info("ScheduleProcess " + initDate.toString());
            FiniteDuration delay = FiniteDuration.create(0, TimeUnit.SECONDS);
            FiniteDuration frequency = FiniteDuration.create(Long.parseLong(Play.application().configuration().getString("videoconverter.intervalSeconds")), TimeUnit.MINUTES);
            sheduler = Akka.system().scheduler().schedule(delay, frequency, execute, Akka.system().dispatcher());
        } catch (Exception e) {
            Logger.error("ScheduleProcess " + e.getMessage());
        }
    }

    public synchronized static ScheduleProcess init() {
        if (converter == null) {
            converter = new ScheduleProcess();
        }
        return converter;
    }

    public void cancel() {
        sheduler.cancel();
        Logger.info("ScheduleProcess.cancel " + ((new Date()).getTime() - initDate.getTime()) / (60 * 1000) + " min");
    }

    public boolean isCancelled() {
        return sheduler.isCancelled();
    }
}