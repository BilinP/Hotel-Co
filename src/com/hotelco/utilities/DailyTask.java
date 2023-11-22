package com.hotelco.utilities;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.hotelco.constants.Constants;
import com.hotelco.entities.ReservationSystem;
/**
 * Creates a task that runs every day. 
 * @author Daniel Schwartz
 */
public class DailyTask {
    /**
     * Creates either a daily check in or check out task that runs at a fixed
     * time.
     * @param hour the hour which triggers this daily task
     * @param checkIn Whether the task should be a check in (else a check out)
     * @author Daniel Schwartz
     */
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
    /**
     * A special class that extends Timertask and gives it a unique run()
     * method.
     * @author Daniel Schwartz
     */
    class CheckInScheduledTask extends TimerTask {
        /**
         * Runs the daily check in task
         */
        public void run() {
            //System.out.println("Running daily check ins");
            ReservationSystem.dailyCheckIn();
        }
    }
    /**
     * A special class that extends Timertask and gives it a unique run()
     * method.
     * @author Daniel Schwartz
     */
    class CheckOutScheduledTask extends TimerTask {
        /**
         * Runs the daily check out task
         */
        public void run() {
            //System.out.println("Running daily check outs");
            ReservationSystem.dailyCheckOut();
        }
    }
    /**
     * Schedules all the daily tasks
     */
    public static void scheduleDailyTasks(){
        new DailyTask(Constants.CHECK_IN_TIME, true);
	    new DailyTask(Constants.CHECK_OUT_TIME, false);
    }
}
