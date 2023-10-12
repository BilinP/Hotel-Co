package com.hotelco.entities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.hotelco.utilities.DatabaseUtil;

public class User {
    
    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    private boolean isEmployee;
    private boolean isManager;
    private String salt;
    private int reservationIds[];

    public User(int id){
        if (DatabaseUtil.doesIdExist(id)){
            //FetchById(id);
        }
    }

    public User(String emailStr){
        email = emailStr;
        if (true)//DatabaseUtil.doesEmailExist(emailStr))
        {
            fetchByEmail(emailStr);
        }
        else{
            //FIXME: handle bad email
        }
    }

    public int getId(){return userId;}

    public String getFirstName(){return firstName;}

    public String getLastName(){return lastName;}

    public String getEmail(){return email;}

    public boolean getIsEmployee(){return isEmployee;}

    public boolean getIsManager(){return isManager;}

    public String getSalt(){return salt;}

    public int [] getReservations(){return reservationIds;}

    public void setId(int newUserId){userId = newUserId;}

    public void setFirstName(String newFirstName){firstName = newFirstName;}

    public void setLastName(String newLastName){lastName = newLastName;}

    public void setEmail(String newEmail){email = newEmail;}

    public void fetchByEmail(String emailStr)
    {
        PreparedStatement ps = null;
        Connection con = null;
        String sqlQuery = null;
        ResultSet rs = null;
        try {
            sqlQuery = "SELECT * FROM 'users' WHERE 'email' = " + emailStr;
            con = ReservationSystem.getDatabaseConnection();
            ps = con.prepareStatement(sqlQuery);
            rs = ps.executeQuery();
            rs.next();
            userId = rs.getInt("user_id");
            firstName = rs.getString("first_name");
            lastName = rs.getString("last_name");
            isEmployee = rs.getBoolean("is_employee");
            isManager = rs.getBoolean("is_manager");
            //FIXME: get reservations
        }
        catch (SQLException e){
            System.out.println(e);
        }
    }
    public void Push(){
    
    

    };


}
