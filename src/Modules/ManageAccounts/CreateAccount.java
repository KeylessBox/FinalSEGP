package Modules.ManageAccounts;

import java.sql.*;

/**
 * Created by Florin on 2/4/2017.
 */
public class CreateAccount {
    public static void createAccount(String name, String surname, String userID, String password, String priv) {

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
            String query = "INSERT into Accounts " +
                    "VALUES ('" + name + "', '" + surname + "', '" + userID + "', '" + password + "', '" + priv + "');";

            Statement st = con.createStatement();
            st.executeUpdate(query);

            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
