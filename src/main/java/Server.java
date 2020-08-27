

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
                    switch (rW.readLine()) {
//Reacting to Android client registered button pressed
                        case "userRegData" :
                            String[] userData = rW.readUserData();
                            Id = Gen.genId(userData[0]);
                            rW.write(Id);
//here will be DB loading for the verification
                            break;
//Reacting to Verification client connected
                        case "adminId":
//if we got unregistered Id in DB we push it to the Verif client and waiting for the t/f respond
                            break;
                        case "userId":
                            Id = rW.read(); //and compare this id to the id in DB
//if verification respond - t
                            Key = Gen.genKey();
                            rW.write(Key);
//Attaching Key to the Id in DB
//if verification respond - null
                            rW.writeLine("wait for verification");
//if verification respond - f
                            rW.writeLine("registration failed");
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
