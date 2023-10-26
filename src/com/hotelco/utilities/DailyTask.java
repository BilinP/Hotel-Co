package com.hotelco.utilities;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.hotelco.entities.ReservationSystem;

public class DailyTask {
    public DailyTask(){
        Timer timer = new Timer(true);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 11);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date time = calendar.getTime();
        timer.scheduleAtFixedRate(new DailyScheduledTask(), time, 86400000);
    }

    class DailyScheduledTask extends TimerTask {
        public void run() {
            ReservationSystem.dailyCheckIn();
            ReservationSystem.dailyCheckOut();
        }
    }

    // public static void main(String args[]) {
	// System.out.println("About to schedule task.");
    //     new Reminder(5);
	// System.out.println("Task scheduled.");
    // }
}
