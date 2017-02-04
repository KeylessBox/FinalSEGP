package Modules.Login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginDB {
    public static Boolean checkUserDetails(String userID, String password) {

        Connection con = null;
      //  PreparedStatement pst = null;

        String url="jdbc:mysql://localhost:3306/wypd_project";
        String user = "root";
        String pw = "pass";

        Boolean check = false;

        try {
            /**
             * Establishes the connection with the database
             */
            con = DriverManager.getConnection(url, user, pw);
            /**
             * The query used to get data
             * In this case, email acts as a username too
             */
            String query = "Select Password from accounts where Email = \"" + userID + "\"  ";
           // pst = con.prepareStatement(query);

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            if(rs.next()) {
               if(rs.getString("Password").equals(password))
                   check = true;
            }
            else

            con.close();
          //  pst.execute();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

  /*      finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (con != null) {
                    con.close();
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }       */

        return check;    }
    
    }



