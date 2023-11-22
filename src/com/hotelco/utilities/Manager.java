package com.hotelco.utilities;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.*;

import com.hotelco.entities.ReservationSystem;

/**
 * Maintains the manager information while giving tools for the manager to
 * maintain a reservation
 * @author John Lee, Daniel Schwartz
 */
public class Manager{

    /**
     * Finds the amount of vacant rooms at the current date
     * @return the number of vacant rooms
     */
    public static Integer findVacantRoomSize(){
        
        Integer result = 0;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sqlQuery = "SELECT room_num " + 
            "FROM rooms " + 
            "WHERE room_num NOT IN (" + 
                "SELECT room_num " + 
                "FROM reservations " + 
                "WHERE start_date <= '" + Date.valueOf(LocalDate.now()) +
                "' OR end_date >= '" + Date.valueOf(LocalDate.now()) + "') "; 
        Connection con = ReservationSystem.getDatabaseConnection();

        try {
            ps = con.prepareStatement(sqlQuery);
            rs = ps.executeQuery();
            if(rs.next()){
                result++;
            }
        }
        catch (SQLException e){
            System.out.println("ReservationSystem.findEmptyRoom()");
            System.out.println(Thread.currentThread().getStackTrace()[2].getLineNumber());
            System.out.println(Thread.currentThread().getStackTrace()[2].getMethodName());
            System.out.println(e);
        }
        ReservationSystem.ready();
        return result;

    }
}
