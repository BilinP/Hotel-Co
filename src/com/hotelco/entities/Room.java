package com.hotelco.entities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.hotelco.utilities.RoomType;
/**
 * Class that structures the details of a room 
 * @author Daniel Schwartz
 * @version %I%, %G%
 */
public class Room {
    /**
     * Represents the room number.
     */
    private Integer roomNum;
    /**
     * Represents the type of room.
     */
    private RoomType roomType;
    /**
     * Represents the group size for a room.
     */
    private Integer maxGroupSize;
    /**
     * Fetches the room number of a room.
     * @param roomNumber
     */
    public Room(Integer roomNumber)
    {
        fetch(roomNumber);
    }
    /**
     * Getter for the room number.
     * @return a room unique room number
     */
    public Integer getRoomNum(){return roomNum;}
    /**
     * Getter for the room type.
     * @return the room type
     */
    public RoomType getRoomType(){return roomType;}
    /**
     * Getter for the maximum group size.
     * @return the group size
     */
    public Integer getMaxGroupSize(){return maxGroupSize;}
    /**
     * Setter for the room Number
     * @param newRoomNum
     */
    public void setRoomNum(Integer newRoomNum){roomNum = newRoomNum;}
    /**
     * Setter for the room type.
     * @param newRoomType
     */
    public void setRoomType(RoomType newRoomType){roomType = newRoomType;}
    /**
     * Setter for the maximum room size.
     * @param newMaxGroupSize
     */
    public void setMaxGroupSize(Integer newMaxGroupSize){maxGroupSize = newMaxGroupSize;}
    /**
     * Fetches the room number with a given room number.
     * @param roomNumToFetch
     */
    public void fetch(Integer roomNumToFetch){
        roomNum = roomNumToFetch;
        fetch();
    }
    /**
     * Fetches a room number through a query.
     */
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

