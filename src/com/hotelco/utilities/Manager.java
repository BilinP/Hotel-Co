package com.hotelco.utilities;

import java.sql.Date;
import java.time.*;

/**
 * Maintains the manager information while giving tools for the manager to maintain a reservation
 * @author John Lee, Daniel Schwartz
 * @version 1.0
 */
public class Manager{

    /**
     * Finds the amount of vacant rooms at the current date
     */
    public static findVacantRoomSize(){
        
        Integer result = 0;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sqlQuery = "SELECT room_num " + 
            "FROM rooms " + 
            "WHERE room_num NOT IN (" + 
                "SELECT room_num " + 
                "FROM reservations " + 
                "WHERE start_date <= '" + Date.valueOf(LocalDate.now) +
                "' OR end_date >= '" + Date.valueOf(LocalDate.now) + "') " 
        Connection con = getDatabaseConnection();

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
        ready();
        return result;

    
}
