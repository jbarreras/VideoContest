package global;

import play.Application;
import play.GlobalSettings;
import utilities.schedule.ScheduleProcess;

public class VideoContestHandler extends GlobalSettings {

    private ScheduleProcess converter;

    public void onStart(Application application) {
        super.onStart(application);

        converter = ScheduleProcess.init();
    }

    public void onStop(Application application) {
        super.onStop(application);

        if (!converter.isCancelled()) {
            converter.cancel();
        }
    }
}
