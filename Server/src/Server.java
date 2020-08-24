import newPack.ReadWr;

import java.io.*;
import java.net.ServerSocket;


public class Server {
    public static void main(String[] args) {

        try (ServerSocket server = new ServerSocket(9000);) {
            System.out.println("Start");
            while (true) {
                try (ReadWr readWr = new ReadWr(server)) {
                    String request = readWr.readLine();
                    String response = "HELLO FROM SERVER: " + request.length();
                    readWr.writeLine(response);

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
