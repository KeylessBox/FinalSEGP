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
             * In this case, it is used to check if the user has administrator privilege
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

    /**
     *
     * @param user gives the email of the user
     * @return true, if the user has admin privileges
     */

    public static Boolean checkPrivilege (String user){

            DBConnection dbConnection = new DBConnection();
            Connection connection = dbConnection.connect();
            Boolean check = false;
            try {

                /**
                 * The query used to get data
                 * In this case, email acts as a username too
                 */
                String query = "Select privileges from accounts where Email = \"" + user + "\"  ";


                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery(query);
                if(rs.next()) {
                    if(rs.getString("privileges").equals("admin"))
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
    




