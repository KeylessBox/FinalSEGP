package modules.table;

/**
 * Created by AndreiM on 2/3/2017.
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    /**
     * Connect to database. For now, only 1 user
     *  //TODO Build it so it can support multiple users
     * @return Connection
     */
    public Connection connect() {

        String url = "jdbc:mysql://localhost:3306/investigationsdb?useSSL=false";
        String username = "root";
        String password = "";

        /**
         * Check if JDBC driver is loaded
         */
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Cannot find the driver in the classpath!", e);
        }

        /**
         * Establish connection with database
         */
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            return connection;

        } catch (SQLException e) {
            System.out.println("e : " + e);

        }
        return null;
    }
}

