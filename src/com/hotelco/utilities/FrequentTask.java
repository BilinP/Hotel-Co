package com.hotelco.utilities;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.hotelco.constants.Constants;
import com.hotelco.entities.ReservationSystem;


public class FrequentTask {
    public FrequentTask(Integer minutesFrequency){
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(
            new DatabaseReconnectTask(),
            new Date(new Date().getTime() + minutesFrequency),
            minutesFrequency);
    }

    class DatabaseReconnectTask extends TimerTask {
        public void run() {
            System.out.println("Reconnecting database");
            //check if system is ready, i.e. there is no active processing
            if (ReservationSystem.isReady()){
                Connection con = ReservationSystem.getDatabaseConnection();
                try{
                    con.close();
                    if (con.isClosed()){
                    }
                    con = ReservationSystem.getDatabaseConnection();
                    if (con.isValid(0)){
                    }
                    ReservationSystem.ready();
                }
                catch (SQLException e){
                    System.out.println(e);
                }
                ReservationSystem.ready();
            }
            else {
                System.out.println("Rescheduling reconnection attempt");
                Timer timer = new Timer(true);
                timer.schedule(
                    new DatabaseReconnectTask(),Constants.RETRY_INTERVAL);
            }
        }
    }

    public static void scheduleFrequentTasks(){
        new FrequentTask(Constants.RECONNECT_INTERVAL);
    }

}
