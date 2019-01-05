package com.mycompany.dbtest;

import java.sql.*;

// Notice, do not import com.mysql.jdbc.*
// or you will have problems!

public class LoadDriver {
    public static void main(String[] args) {
        try {
            // The newInstance() call is a work around for some
            // broken Java implementations

            Class.forName("com.mysql.jdbc.Driver").newInstance();
            //Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

            Connection conn = null;
            try {
                conn =
                        DriverManager.getConnection("jdbc:mysql://localhost/setupproject?" +
                                "user=root&password=qwerty");
                // Do something with the Connection
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT id FROM users");
                while (rs.next()) {
                    String lastName = rs.getString("id");
                    System.out.println("The lastname is :"+lastName + "\n");
                }
            } catch (SQLException ex) {
                // handle any errors
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
            }
        } catch (Exception ex) {
            // handle the error
        }
    }
}
