package com.hotelco.entities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.hotelco.utilities.RoomType;

public class Room {
    
    private int roomNum;

    private RoomType roomType;

    private boolean isOccupied;

    private User occupant;

    private int maxGroupSize;

    public Room(){}
    
    public Room(int roomNumber)
    {
        fetch(roomNumber);
    }

    public int getRoomNum(){return roomNum;}

    public RoomType getRoomType(){return roomType;}

    public boolean getIsOccupied(){return isOccupied;}

    public User getOccupant(){return occupant;}

    public int getMaxGroupSize(){return maxGroupSize;}

    public void setRoomNUm(int newRoomNum){roomNum = newRoomNum;}

    public void setRoomType(RoomType newRoomType){roomType = newRoomType;}

    public void setIsOccupied(boolean newIsOccupied){isOccupied = newIsOccupied;}

    public void setOccupant(User newOccupant){occupant = newOccupant;}

    public void setMaxGroupSize(int newMaxGroupSize){maxGroupSize = newMaxGroupSize;}

    public void fetch(int roomNumToFetch){
        PreparedStatement ps = null;
        Connection con = null;
        String sqlQuery = null;
        ResultSet rs = null;
        try {
            sqlQuery = "SELECT * FROM rooms WHERE room_num = " + roomNumToFetch;
            con = ReservationSystem.getDatabaseConnection();
            ps = con.prepareStatement(sqlQuery);
            rs = ps.executeQuery();
            rs.next();
            roomNum = roomNumToFetch;
            roomType = RoomType.valueOf(rs.getString("room_type").toUpperCase());
            isOccupied = rs.getBoolean("is_occupied");
            occupant = new User(rs.getInt("user_id"));
            maxGroupSize = rs.getInt("max_group_size");
        }
        catch (SQLException e){
            System.out.println(e);
        }
    }
}

