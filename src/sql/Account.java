package sql;

import java.sql.*;
import sql.DBConnection;

/**
 * Created by Florin on 2/4/2017.
 * Account class that contains account related functionality
 */
public class Account {
    /**
     * Creates an account by sending the information to database.
     * @param name  Name of user
     * @param surname   Surname of user
     * @param userEmail Email of user
     * @param password  Password, not encrypted
     * @param userPrivilege degree of influence: user or administrator
     */
    public static void createAccount(String name, String surname, String userEmail, String password, String userPrivilege) {
        DBConnection dbConnection = new DBConnection();
        Connection connection = dbConnection.connect();
        try {
//          The query is used to store account details into database
            String query = "INSERT INTO accounts (name,surname,email,password, privileges) VALUES ('" +
                    name + "', '" + surname + "', '" + userEmail + "', '" + password + "', '" + userPrivilege + "');";

            Statement st = connection.createStatement();
            st.executeUpdate(query);

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes the account from database using the email as identifier.
     * @param userEmail
     */
    public static void deleteAccount(String userEmail) {
        DBConnection dbConnection = new DBConnection();
        Connection connection = dbConnection.connect();

        try {
//          The query is used to delete the account from the database
            String query = "DELETE from ACCOUNTS where EMAIL = '" + userEmail + "';";

            Statement st = connection.createStatement();
            st.executeUpdate(query);

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the name and the surname of the account.
     * @param userEmail the email used by the specific account
     * @return Name and Surname of the account
     */
    public static String userGetName(String userEmail)  {
        DBConnection dbConnection = new DBConnection();
        Connection connection = dbConnection.connect();
        String result = "";

        try {
//          Query used to retrieve account information from the database
            String query = "Select * from accounts where Email = \"" + userEmail + "\"  ";

            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);

//          It takes only the name and surname from the above data
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
            System.err.println("Something wrong with closing the database connection");
            e.printStackTrace();
        }
        return result;
    }
}
