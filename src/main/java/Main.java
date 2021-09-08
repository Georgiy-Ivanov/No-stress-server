import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;


public class Main{

    static Scanner sc;
    ArrayList <UserServer> users = new ArrayList<>();
    ServerSocket server;

    Main() throws IOException {
        server = new ServerSocket(1234);
    }

    public void run(){
        String pass = "000000";
        System.out.println("waiting for clients");
        while (true) {
            try {
                Socket socket = server.accept();
                System.out.println("New client connected!");
                users.add(new UserServer(socket, this, pass));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public  static void main(String[] args){
        try {
            new Main().run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}