package com.hotelco.utilities;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.hotelco.constants.Constants;
import com.hotelco.entities.ReservationSystem;

public class DailyTask {
    public DailyTask(Integer hour){
        System.out.println("Setting timer");
        Timer timer = new Timer(true);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date time = calendar.getTime();
        timer.scheduleAtFixedRate(new DailyScheduledTask(), time, 86400000);
    }

    class DailyScheduledTask extends TimerTask {
        public void run() {
            System.out.println("Running daily check ins");
            ReservationSystem.dailyCheckIn();
            System.out.println("Running daily check outs");
            ReservationSystem.dailyCheckOut();
        }
    }
    public static void runDailyTasks(){
        new DailyTask(Constants.CHECK_IN_TIME);
	    new DailyTask(Constants.CHECK_OUT_TIME);
    }
}
