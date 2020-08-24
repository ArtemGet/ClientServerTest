import newPack.ReadWr;

import java.io.*;



public class Client {

    public static void main(String[] args) {
        try (ReadWr readWr = new ReadWr("192.168.0.110",9000))
        {
            System.out.println("connected");
            String request = "smthng";
            readWr.writeLine(request);
            String response = readWr.readLine();
            System.out.println("Request: " + request);
            System.out.println("Response: " + response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
