package com.hotelco.entities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.hotelco.constants.RoomType;
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
     * Represents the max group size for this room.
     */
    private Integer maxGroupSize;
    /**
     * Creates a room with supplied room number. To be used
     * immediately with fetch().
     * @param roomNumber room number to be associated with this room
     */
    public Room(Integer roomNumber)
    {
        roomNum = roomNumber;
    }
    /**
     * Gets the room number associated with this room.
     * @return the room number associated with this room.
     */
    public Integer getRoomNum(){return roomNum;}
    /**
     * Gets the room type associated with this room.
     * @return the room type associated with this room.
     */
    public RoomType getRoomType(){return roomType;}
    /**
     * Gets the maximum group size associated with this room.
     * @return the group size
     */
    public Integer getMaxGroupSize(){return maxGroupSize;}
    /**
     * Sets the room number.
     * @param newRoomNum the room number to be associated with this room.
     */
    public void setRoomNum(Integer newRoomNum){roomNum = newRoomNum;}
    /**
     * Sets the room type.
     * @param newRoomType the room type to be associated with this room.
     */
    public void setRoomType(RoomType newRoomType){roomType = newRoomType;}
    /**
     * Sets the maximum number of occupants allowed in this room.
     * @param newMaxGroupSize the maximum occupancy to be associated with this room.
     */
    public void setMaxGroupSize(Integer newMaxGroupSize){
        maxGroupSize = newMaxGroupSize;}
    /**
     * Fetches the room with a given room number from the database.
     * @param roomNumToFetch room number to use for database fetch
     */
    public void fetch(Integer roomNumToFetch){
        roomNum = roomNumToFetch;
        fetch();
    }
    /**
     * Fetches a room from the database.
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
                roomType = RoomType.valueOf(
                    rs.getString("room_type").toUpperCase());
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

