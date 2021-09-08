import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;


class DBConnectionTest {
    DBConnection dbConnection;

    @BeforeEach
    void prepare(){
        String pass = "000000";
        dbConnection = new DBConnection(pass);
    }

    @Test
    void testForAllDbConnections() {
        assertTrue(dbConnection.registration("login432@test.ru", "Pas"), () -> "User should be added");
        assertTrue(dbConnection.loginIn("login432@test.ru", "Pas"), () -> "User should be login");
        assertTrue(dbConnection.createProfile("login432@test.ru", "1992-05-05", "Test", "00000000000"), () -> "Profile should be created");
        assertTrue(dbConnection.getUserInfo("login432@test.ru").equals("get;1992-05-05;Test;00000000000"), () -> "User should be login");
        assertTrue(dbConnection.deleteUserInfo("login432@test.ru"), () -> "User_info should be deleted");
        assertTrue(dbConnection.deleteUser("login432@test.ru"), () -> "User should be deleted");
    }
}