package com.hotelco.connections;

import java.sql.*;
/**
* Creates and maintains a connection to a pre-defined database.
*
* @author      Daniel Schwartz
* @version     %I%, %G%
*/
public class DatabaseConnection {
/**
 * Holds an open connection to the database.
 */
    private static Connection con = connectDB();
/**
 * Connects static member con to the database
 * @return Connection object, connected to db
 */
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