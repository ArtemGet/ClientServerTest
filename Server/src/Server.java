import newPack.ReadWr;

import java.io.*;
import java.net.ServerSocket;


public class Server {
    public static void main(String[] args) {

        try (ServerSocket server = new ServerSocket(9000);) {
            System.out.println("Start");
            while (true) {
                try (ReadWr rW = new ReadWr(server)) {
                    while (!server.isClosed()) {

                        switch (rW.readLine()) {

                            case "userRegData" :
                                String[] userData = rW.readUserData();
                                int Id = Gen.genId(userData[0]);
                                int Key = Gen.genKey();
                                
                                rW.write(Id);
                                rW.write(Key);
                                break;
                        }
                    }
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
