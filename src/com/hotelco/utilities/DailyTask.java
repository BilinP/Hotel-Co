package com.hotelco.utilities;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.hotelco.constants.Constants;
import com.hotelco.entities.ReservationSystem;

public class DailyTask {
    public DailyTask(Integer hour, Boolean checkIn){
        Timer timer = new Timer(true);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date time = calendar.getTime();
        if (checkIn){
            timer.scheduleAtFixedRate(new CheckInScheduledTask(), time,
            86400000);
        }
        else {
            timer.scheduleAtFixedRate(new CheckOutScheduledTask(), time,
            86400000);
        }
    }

    class CheckInScheduledTask extends TimerTask {
        public void run() {
            System.out.println("Running daily check ins");
            ReservationSystem.dailyCheckIn();
        }
    }

    class CheckOutScheduledTask extends TimerTask {
        public void run() {
            System.out.println("Running daily check outs");
            ReservationSystem.dailyCheckOut();
        }
    }

    public static void scheduleDailyTasks(){
        new DailyTask(Constants.CHECK_IN_TIME, true);
	    new DailyTask(Constants.CHECK_OUT_TIME, false);
    }
}
