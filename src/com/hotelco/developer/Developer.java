package com.hotelco.developer;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.hotelco.constants.RoomType;
import com.hotelco.entities.ReservationSystem;
import com.hotelco.entities.User;
import com.hotelco.utilities.DatabaseUtil;

import javafx.scene.chart.PieChart.Data;

/**
 * Contains the developer sandbox and methods usefull to developers
 */
public class Developer {
    /**
     * Developer sandbox to run snippets of code on their own without launching
     * main.
     */
    public static void runDevMode(){
        //run1();
        //run2();
        run3();
    }

    public static void run1(){
        setUserEmail("p@p.com", "daniel.schwartz.447@my.csun.edu");
    }
    public static void run2(){
        setUserEmail("daniel.schwartz.447@my.csun.edu", "p@p.com");
    }
    public static void run3(){
        System.out.println(DatabaseUtil.doesEmailExist("daniel.schwartz.447@my.csun.edu"));
    }

    public static void setUserEmail(String oldEmail, String newEmail){
        Integer userId = new User(oldEmail, false).getUserId();
        DatabaseUtil.ready();
        PreparedStatement ps = null;
        String sqlQuery = "UPDATE users " +
            "SET email = '" + newEmail + "' " +
            "WHERE user_id = " + userId;
        Connection con = ReservationSystem.getDatabaseConnection();
        System.out.println(sqlQuery);

        try {
            ps = con.prepareStatement(sqlQuery);
            ps.execute();
        }
        catch (SQLException e){
            System.out.println("DevMode.setUserEmail()");
            System.out.println(Thread.currentThread().getStackTrace()[2].getLineNumber());
            System.out.println(Thread.currentThread().getStackTrace()[2].getMethodName());
            System.out.println(e);
        }
        DatabaseUtil.ready();
    }
}
