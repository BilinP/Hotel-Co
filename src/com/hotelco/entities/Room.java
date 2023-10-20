package com.hotelco.entities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.hotelco.utilities.RoomType;

public class Room {
    
    private Integer roomNum;

    private RoomType roomType;

    private Integer maxGroupSize;
    
    public Room(Integer roomNumber)
    {
        fetch(roomNumber);
    }

    public Integer getRoomNum(){return roomNum;}

    public RoomType getRoomType(){return roomType;}

    public Integer getMaxGroupSize(){return maxGroupSize;}

    public void setRoomNUm(Integer newRoomNum){roomNum = newRoomNum;}

    public void setRoomType(RoomType newRoomType){roomType = newRoomType;}

    public void setMaxGroupSize(Integer newMaxGroupSize){maxGroupSize = newMaxGroupSize;}

    public void fetch(Integer roomNumToFetch){
        roomNum = roomNumToFetch;
        fetch();
    }
    public void fetch(){
        PreparedStatement ps = null;
        Connection con = null;
        String sqlQuery = null;
        ResultSet rs = null;
        try {
            sqlQuery = "SELECT * FROM rooms WHERE room_num = " + roomNum;
            con = ReservationSystem.getDatabaseConnection();
            ps = con.prepareStatement(sqlQuery);
            rs = ps.executeQuery();
            if(rs.next())
            {
                roomType = RoomType.valueOf(rs.getString("room_type").toUpperCase());
                maxGroupSize = rs.getInt("max_group_size");
            }

        }
        catch (SQLException e){
            System.out.println(e);
        }
    }

    // public boolean isOccupied(){

    //     return true;
    // }

    // public User getOccupant(){
    // }
}

