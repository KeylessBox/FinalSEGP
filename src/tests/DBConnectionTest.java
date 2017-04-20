package tests;

import sql.DBConnection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by AndreiM on 2/27/2017.
 */
class DBConnectionTest {
    DBConnection dbConnection;
    @BeforeEach
    void setUp() {
        dbConnection = new DBConnection();
    }

    @AfterEach
    void tearDown() {
        dbConnection = null;
    }

    @Test
    void connect() {
        Connection result = dbConnection.connect();
        assertNotNull(result);
    }

}