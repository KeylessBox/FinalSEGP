package Modules.ManageAccounts;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Florin on 2/4/2017.
 */
public class DeleteAccount {
    public static void deleteAccount(String userID) {

        Connection con = null;


        String url = "jdbc:mysql://localhost:3306/investigationsdb";
        String user = "root";
        String pw = "";


        try {
            /**
             * Establishes the connection with the database
             */
            con = DriverManager.getConnection(url, user, pw);
            /**
             * The query is used to store
             * Account Details into Database
             */
            String query = "DELETE from ACCOUNTS " +
                    "where EMAIL = '" + userID + "';";

            Statement st = con.createStatement();
            st.executeUpdate(query);

            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

}
