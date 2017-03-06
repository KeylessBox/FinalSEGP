package modules.manageAccounts;

import modules.table.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Florin on 2/4/2017.
 */
public class DeleteAccount {
    public static void deleteAccount(String userID) {

        DBConnection dbConnection = new DBConnection();

        Connection connection = dbConnection.connect();

        try {
            /**
             * The query is used to store
             * Account Details into Database
             */
            String query = "DELETE from ACCOUNTS " +
                    "where EMAIL = '" + userID + "';";

            Statement st = connection.createStatement();
            st.executeUpdate(query);

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

}
