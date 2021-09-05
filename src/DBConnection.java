import java.sql.*;
import java.util.Scanner;

public class DBConnection {
    Scanner sc;
    private  final static String  HOST     = "localhost"  ; // сервер базы данных
    private  final static String  DATABASENAME = "postgres"  ;// имя базы
    private  final static String  USERNAME = "postgres"; // учетная запись пользователя
    String PASSWORD;
    String url;


    public DBConnection (){
        //sc = new Scanner(System.in);
        System.out.println("Введите пароль от базы");
        sc = new Scanner(System.in);
        PASSWORD = sc.nextLine(); // пароль
        url="jdbc:postgresql://"+HOST+"/"+DATABASENAME+"?user="+USERNAME+"&password="+PASSWORD;
    }

    public ResultSet bdReqest(String SQL) throws SQLException {

        ResultSet resultSet = null;
        try {
            Connection connection = DriverManager.getConnection(url, USERNAME, PASSWORD);
            if (connection == null) {
                System.out.println("Нет соединения с БД!");
            } else {
                //Запрос на получение всех данных
                PreparedStatement preparedStatement = connection.prepareStatement(SQL);
                System.out.println("база отработала");
                    resultSet = preparedStatement.executeQuery();

            }
        } catch (Exception e) {
            System.out.println("база не отработала");
            e.printStackTrace();
        }
        return resultSet;
    }
}

