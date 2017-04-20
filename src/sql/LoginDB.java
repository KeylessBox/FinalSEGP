package sql;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Database connectivity class
 */
public class LoginDB {

    public static Boolean checkUserDetails(String userID, String password) {
        DBConnection dbConnection = new DBConnection();
        Connection connection = dbConnection.connect();
        Boolean check = false;

        try {

            /**
             * The query used to get data
             * In this case, email acts as a username too
             */
            String query = "Select Password from accounts where Email = \"" + userID + "\"  ";


            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
            if(rs.next()) {
               if(rs.getString("Password").equals(password))
                   check = true;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return check;    }
    
    }



