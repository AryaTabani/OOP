package com.example.projectgraphic;

import java.sql.*;

public class database {
    public database() {
    }

    public static Connection connectDB() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/foodi", "root", "");
            return connect;
        } catch (Exception var1) {
            var1.printStackTrace();
            return null;
        }
    }
}