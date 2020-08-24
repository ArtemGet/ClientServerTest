import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {

        try (ServerSocket server = new ServerSocket(9000);) {
            System.out.println("Start");
            while (true) {
                try (Socket socket = server.accept();
                     BufferedWriter writer =
                             new BufferedWriter(
                                     new OutputStreamWriter(
                                             socket.getOutputStream()));
                     BufferedReader reader =
                             new BufferedReader(
                                     new InputStreamReader(
                                             socket.getInputStream()));
                ) {

                    String request = reader.readLine();

                    String response = "HELLO FROM SERVER: " + request.length();
                    System.out.println("HELLO FROM SERVER: " + request.length());
                    writer.write(response);
                    System.out.println("Request: " + request);
                    writer.newLine();
                    writer.flush();


                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
            }

        catch (IOException e) {
            throw new RuntimeException(e);
        }





    }
}
