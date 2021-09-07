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
        String request;
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            Thread.sleep(500);
            request = reader.readLine();
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
        String q = "2";
        String[] massStrings;
        try {
            massStrings = msg.split(";");
            if (massStrings[0].equals("reg")) {
                if (dbConnection.registration(massStrings[1], massStrings[2])) {
                    q = "1";
                }
            }
            if (massStrings[0].equals("log")) {
                if (dbConnection.loginIn(massStrings[1], massStrings[2])){
                    q = "1";
                }
            }
            if (massStrings[0].equals("get")) {
                q = dbConnection.getUserInfo(massStrings[1]);
            }
            if (massStrings[0].equals("create")) {
                if (dbConnection.createProfile(massStrings[1], massStrings[2], massStrings[3], massStrings[4])) {
                    q = "1";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return q;
    }

}
