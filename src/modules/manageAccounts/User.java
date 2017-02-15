package modules.manageAccounts;

import modules.table.DBConnection;

import java.sql.*;

/**
 * Created by Florin on 2/6/2017.
 */
public class User {
    public static String userGetName(String userID)  {
        DBConnection dbConnection = new DBConnection();

        Connection connection = dbConnection.connect();

        String result = "";

        try {
            /**
             * The query used to get data
             * In this case, email acts as a username too
             */
            String query = "Select * from accounts where Email = \"" + userID + "\"  ";


            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
            if(rs.next()) {
                result = rs.getString("Name") + " " + rs.getString("Surname");
            }


        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            connection.close();
        } catch (SQLException e) {

        }
        return result;    }

}


