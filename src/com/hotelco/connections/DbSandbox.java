package com.hotelco.connections;
import java.sql.*;

public class DbSandbox {
    public static void ListAllRooms() {

        Connection con = null;
        PreparedStatement p = null;
        ResultSet rs = null;

        con = DatabaseConnection.connectDB();

        // try {

        //     String sql = "SELECT * FROM `rooms`";
        //     p = con.prepareStatement(sql);
        //     rs = p.executeQuery();


        //     System.out.println("room num\t\troom type\t\toccupation\t\tcustomer id");

        //     // Condition check
        //     while (rs.next()) {

        //         int roomNum = rs.getInt("room_num");
        //         String roomType = rs.getString("room_type");
        //         boolean isOccupied = rs.getBoolean("is_Occupied");
        //         int customerId = rs.getInt("customer_id");
        //         System.out.println(roomNum + "\t\t" + roomType
        //                 + "\t\t" + isOccupied + "\t\t" + customerId);
        //     }
        // }

        // // Catch block to handle exception
        // catch (SQLException e) {

        //     // Print exception pop-up on screen
        //     System.out.println(e);
        // }
    }
}
