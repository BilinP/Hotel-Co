package com.hotelco.connections;

import java.sql.*;

public class DatabaseConnection {

    private static Connection con = connectDB();

    public static Connection connectDB()
    {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection(
                    "jdbc:mysql://sql3.freemysqlhosting.net:3306/sql3651321",
                    "sql3651321", "fB8SKw8fPQ");

            return con;
        }

        catch (SQLException | ClassNotFoundException e)
        {
            System.out.println(e);

            return null;
        }
    }
}