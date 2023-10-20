package com.hotelco.entities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.hotelco.utilities.RoomType;

public class Room {
    
    private Integer roomNum;

    private RoomType roomType;

    private boolean isOccupied;

    private User occupant;

    private Integer maxGroupSize;

    public Room(){}
    
    public Room(Integer roomNumber)
    {
        fetch(roomNumber);
    }

    public Integer getRoomNum(){return roomNum;}

    public RoomType getRoomType(){return roomType;}

    public boolean getIsOccupied(){return isOccupied;}

    public User getOccupant(){return occupant;}

    public Integer getMaxGroupSize(){return maxGroupSize;}

    public void setRoomNUm(IntegernewRoomNum){roomNum = newRoomNum;}

    public void setRoomType(RoomType newRoomType){roomType = newRoomType;}

    public void setIsOccupied(boolean newIsOccupied){isOccupied = newIsOccupied;}

    public void setOccupant(User newOccupant){occupant = newOccupant;}

    public void setMaxGroupSize(Integer newMaxGroupSize){maxGroupSize = newMaxGroupSize;}

    public void fetch(Integer roomNumToFetch){
        PreparedStatement ps = null;
        Connection con = null;
        String sqlQuery = null;
        ResultSet rs = null;
        try {
            sqlQuery = "SELECT * FROM rooms WHERE room_num = " + roomNumToFetch;
            con = ReservationSystem.getDatabaseConnection();
            ps = con.prepareStatement(sqlQuery);
            rs = ps.executeQuery();
            if(rs.next())
            {
                roomNum = roomNumToFetch;
                roomType = RoomType.valueOf(rs.getString("room_type").toUpperCase());
                isOccupied = rs.getBoolean("is_occupied");
                occupant = new User(rs.getInt("user_id"));
                maxGroupSize = rs.getInt("max_group_size");
            }

        }
        catch (SQLException e){
            System.out.println(e);
        }
    }
}

