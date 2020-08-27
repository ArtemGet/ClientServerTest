

import java.io.*;
import java.net.ServerSocket;


public class Server {
    public static void main(String[] args) {

        try (ServerSocket server = new ServerSocket(9000);) {
            System.out.println("Start");
            while (true) {
                try (RW rW = new RW(server)) {
                    int Id;
                    int Key;
                    DBAccess a;
                    switch (rW.readLine()) {
//Reacting to Android client registered button pressed
                        case "userRegData" :
                            Object[] userData = rW.readUserData();
                            Id = Gen.genId((String) userData[0]);
                            rW.write(Id);

                            a = new DBAccess();
                            a.setUserData((String) userData[0],(String)userData[1], (int)userData[2]);
                           // rW.writeLine("Wait for 24 hours");
                            break;
//Reacting to Verification client connected
                        case "adminId":

                            break;
                        case "userId":
                            Id = rW.read();

                             a = new DBAccess();
                            int key = a.checkKey(Id);
                            if (key != 0) {
                                rW.write(key);
                            }
                            else {
                                rW.close();
                            }
                            break;
                        case "userHelpRequest":
                            String[] help = rW.readHelp();
                            rW.writeLine("Help is incoming");
//Attaching help[] request to the id the DB
                            break;
                        case  "socId":
//If we got help request in DB push it to the soc client
//Will add logic later
                            break;
                        case "userKey":
                            Key = rW.read();
//compare it to the key in DB
//if true
                            rW.writeLine("On");
//If false
                            rW.writeLine("Off");
                            break;

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
