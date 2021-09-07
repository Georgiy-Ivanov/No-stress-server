import java.sql.*;

public class DBConnection {
    private  final static String  HOST     = "localhost"  ; // сервер базы данных
    private  final static String  DBNAME = "postgres"  ;// имя базы
    private  final static String  USERNAME = "postgres"; // учетная запись пользователя
    String PASSWORD;
    String url;
    private Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet = null;


    public DBConnection (String pass){
        PASSWORD = pass;
        System.out.println(PASSWORD);
        url="jdbc:postgresql://"+HOST+"/"+DBNAME+"?user="+USERNAME+"&password="+PASSWORD;
        try {
            connection = DriverManager.getConnection(url, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean registration(String login, String pass){
        try {
            String SQL = "Insert into users (login, pass) values (? , ?);";
            preparedStatement = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, pass);
            int rows = preparedStatement.executeUpdate();
            if (rows == 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean loginIn(String login, String pass){
        try {
            String SQL = "SELECT login, pass FROM users WHERE login=? AND pass=?;";
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, pass);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getUserInfo(String email){
        try {
            String SQL = "SELECT date, name, phone FROM users_info WHERE email =?;";
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return "get;" +resultSet.getString("date") + ";"
                        + resultSet.getString("name") + ";" +resultSet.getString("phone");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "2";
    }

    public boolean createProfile(String email, String date, String name, String phone){
        try {
            String SQL = "Insert into users_info (email, date, name, phone) values (?, ?, ?, ?);";
            preparedStatement = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, date);
            preparedStatement.setString(3, name);
            preparedStatement.setString(4, phone);
            int rows = preparedStatement.executeUpdate();
            if (rows == 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

