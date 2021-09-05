import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;

public class Main
{
    ArrayList <UserServer> users = new ArrayList<>();
    ServerSocket server;


    Main() throws IOException {
        server = new ServerSocket(1234);
    }
    public void run() throws IOException, SQLException {
        while (true){
            System.out.println("waiting for clients");
            Socket socket = server.accept();
            System.out.println("New client connected!");
            users.add(new UserServer(socket, this));
        }
    }




    public  static void main(String[] args) throws IOException, SQLException {
        new Main().run();
    }
}