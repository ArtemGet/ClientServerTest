import newPack.ReadWr;

import java.io.*;
import java.net.ServerSocket;


public class Server {
    public static void main(String[] args) {

        try (ServerSocket server = new ServerSocket(9000);) {
            System.out.println("Start");
            while (true) {
                try (ReadWr readWr = new ReadWr(server)) {
                    String[] one =  readWr.readUserData();
                    int Id = Gen.genId(one[0]);
                    int Key = Gen.genKey();
                    readWr.writeId(Id);
                    readWr.writeKey(Key);
                    System.out.println(Key);

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
