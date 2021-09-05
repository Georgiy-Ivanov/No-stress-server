import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client{
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {

            try (Socket socket = new Socket("127.0.0.1", 1234);
                 BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                 BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                while (true) {
                    System.out.println("Enter message: ");
                    writer.write(sc.nextLine());
                    writer.newLine();
                    writer.flush();
                    System.out.println(reader.readLine());
                }

            } catch (IOException e) {
                e.printStackTrace();
            }



    }
}
