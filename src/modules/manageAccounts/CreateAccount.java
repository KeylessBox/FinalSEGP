package modules.manageAccounts;

import java.sql.*;
import modules.table.DBConnection;
/**
 * Created by Florin on 2/4/2017.
 */
public class CreateAccount {
    public static void createAccount(String name, String surname, String userID, String password, String priv) {

        DBConnection dbConnection = new DBConnection();

        Connection connection = dbConnection.connect();


        try {
            /**
             * The query is used to store
             * Account Details into Database
             */
            String query = "INSERT INTO accounts (name,surname,email,password, privileges)" +
                    "VALUES ('" + name + "', '" + surname + "', '" + userID + "', '" + password + "', '" + priv + "');";

            Statement st = connection.createStatement();
            st.executeUpdate(query);

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        createAccount("Mitica", "Bulversatul", "m@i.t","pass", "lel");
    }

}
