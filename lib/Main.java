import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

    //private static final String URL = "jdbc:postgresql//127.0.0.1:5432/postgres", "user=postgres", "password=000000";

    private static String conok="Соединение с бд установлено";
    private static String conerr="Произошла ошибка подключения к бд";

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql//127.0.0.1:5432/postgres", "postgres", "000000")){
            System.out.println(String.format("%s",conok));
        } catch (SQLException e) {
            System.out.println(String.format("%s",conerr));
            e.printStackTrace();
        }
    }
}