package Modules.ManageAccounts;

import java.sql.*;

/**
 * Created by Florin on 2/6/2017.
 */
public class User {
    public static String userGetName(String userID)  {

        Connection con = null;

        String url="jdbc:mysql://localhost:3306/investigationsdb";
        String user = "root";
        String pw = "";

        String result = "";

        try {
            /**
             * Establishes the connection with the database
             */
            con = DriverManager.getConnection(url, user, pw);
            /**
             * The query used to get data
             * In this case, email acts as a username too
             */
            String query = "Select * from accounts where Email = \"" + userID + "\"  ";


            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            if(rs.next()) {
                result = rs.getString("Name") + " " + rs.getString("Surname");
            }


        }
        catch (SQLException e) {
            e.printStackTrace();
        }


        return result;    }

}


