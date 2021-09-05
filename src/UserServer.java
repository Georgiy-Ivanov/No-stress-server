import java.io.*;
import java.net.Socket;
import java.sql.*;

public final class UserServer implements Runnable{
    Main server;
    Socket socket;
    ResultSet answer;
    DBConnection dbConnection;

    public UserServer(Socket socket, Main server) throws SQLException {
        this.socket = socket;
        this.server = server;
        new Thread(this).start();
        dbConnection = new DBConnection();
    }


    @Override
    public void run() {
        String request;
        String q;
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

                Thread.sleep(500);
                request = reader.readLine();
                System.out.println("Request: " + request);
                writer.write(receivedMsg(request));
            System.out.println("Отправили клиенту");
                writer.newLine();
                writer.flush();
        } catch (IOException | SQLException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String receivedMsg(String msg) throws SQLException {
        String q = "2";
        if (regAndLogInSql(msg)){
            System.out.println("Отправили 1");
            return "1";
        }
        if (createProfile(msg)) {
            System.out.println("Отправили 1");
            return "1";
        }
        if (!(q = getUserInfo(msg)).equals("nope")) {
            System.out.println("q :" + q);
            return q;
        }
        System.out.println("чёта отправилось");
        return q;
    }

    private boolean createProfile(String request) throws SQLException {
        String[] dataProfile;
        try {
            dataProfile = request.split(";");
            if (dataProfile[0].equals("create")){
                answer = dbConnection.bdReqest("Insert into users_info (email, date, name, phone)" +
                        " values ('" + dataProfile[1] + "','" + dataProfile[2] + "','" + dataProfile[3] + "','" + dataProfile[4] + "');");
                if (answer.next()) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (Exception e) { e.printStackTrace();}
        return false;
    }

    private boolean regAndLogInSql(String requset) throws SQLException {
        String[] logpass;
        try {
            logpass = requset.split(";");
            if (logpass[0].equals("reg")){
                answer = dbConnection.bdReqest("Insert into users (login, pass) values ('" + logpass[1] + "','" + logpass[2] + "');");
                if (answer.next()) {
                    return true;
                } else {
                    return false;
                }
            }
            if (logpass[0].equals("log")) {
                answer = dbConnection.bdReqest("SELECT login, pass FROM users WHERE login='" + logpass[1] + "' AND pass='" + logpass[2] + "';");
                if (answer.next()) {
                    return true;

                } else {
                    return false;
                }

            }
        } catch (Exception e) { }
        return false;

    }

    private String getUserInfo(String requset) throws SQLException {
        String[] mass;

        try {

            mass = requset.split(";");

            if (mass[0].equals("get")){

                    answer = dbConnection.bdReqest("SELECT date, name, phone FROM users_info WHERE email ='" + mass[1] +"';");

                if (answer.next()) {
                    System.out.println("всё пока работает");
                    return "get;" +answer.getString("date") + ";" + answer.getString("name") + ";" +answer.getString("phone");
                } else {
                    System.out.println("nope");
                    return "nope";
                }
            }
        } catch (Exception e) { }
        return "nope";
    }
}
