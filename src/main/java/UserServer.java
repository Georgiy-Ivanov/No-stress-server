import java.io.*;
import java.net.Socket;

public final class UserServer implements Runnable{
    Main server;
    Socket socket;
    DBConnection dbConnection;



    public UserServer(Socket socket, Main server, String pass) {
        this.socket = socket;
        this.server = server;
        new Thread(this).start();
        dbConnection = new DBConnection(pass);
    }


    @Override
    public void run() {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            Thread.sleep(500);
            String request = reader.readLine();
            System.out.println("Request: " + request);
            writer.write(receivedMsg(request));
            System.out.println("Отправили клиенту");
            writer.newLine();
            writer.flush();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String receivedMsg(String msg) {
        String[] massStrings;
        try {
            massStrings = msg.split(";");
            if (massStrings[0].equals("reg")) {
                if (dbConnection.registration(massStrings[1], massStrings[2])) {
                    return "1";
                }
            }
            if (massStrings[0].equals("log")) {
                if (dbConnection.loginIn(massStrings[1], massStrings[2])){
                    return "1";
                }
            }
            if (massStrings[0].equals("get")) {
                return dbConnection.getUserInfo(massStrings[1]);
            }
            if (massStrings[0].equals("create")) {
                System.out.println(massStrings[2]);
                if (dbConnection.createProfile(massStrings[1], massStrings[2], massStrings[3], massStrings[4])) {
                    return "1";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "2";
    }

}
