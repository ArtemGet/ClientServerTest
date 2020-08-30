

import java.io.*;
import java.net.ServerSocket;
import java.util.ArrayList;


public class Server {
    public static void main(String[] args) {

        try (ServerSocket server = new ServerSocket(9000);) {
            System.out.println("Start");
            while (true) {
                try (RW rW = new RW(server)) {
                    int Id;
                    int key;
                    DBAccess a;
                    switch (rW.readLine()) {
//Registration Android Client
                        case "userRegData" :
                            a = new DBAccess();
                            Object[] userData = rW.readUserData();
                            System.out.println("connected");
                            a.setUserData((String) userData[0],(String)userData[1], (int)userData[2]);
                            System.out.println(a.getId((String) userData[0],(String) userData[1],(int)userData[2]));
                            //добавить инд ключ для id
                            rW.write(a.getId((String) userData[0],(String) userData[1],(int)userData[2]));
                            break;
//Adding key to Android Client after verification
                        case "userId":
                            //заглушка
                            Id = rW.read();
                             key = Gen.genKey();
                            a = new DBAccess();
                            System.out.println(Id);
                            System.out.println(key);
                            a.insertKey(Id, key);

                            rW.write(key);
                            /* a = new DBAccess();
                            int key = a.checkKey(Id);
                            if (key != 0) {
                                rW.write(key);
                            }
                            else {
                                rW.close();
                            } */
                            break;
                        //Verify Android Client
                        case "adminId":
                            System.out.println("connected");
                            a = new DBAccess();
                            ArrayList<Integer> ID = a.getUnverifiedId();
                            ArrayList<String> DATA =  a.getUnverifiedData();
                            rW.writeUnverified(ID,DATA);

                            ArrayList<Integer> verifiedId =  rW.readVerifiedId();
                            //System.out.println(verifiedId.size());
                            for (int b:verifiedId
                                 ) {
                                a.insertKey(b,Gen.genKey());
                            }

                            //while (rW.readLine() != "disconnect") {
                            //    Id = rW.read();
                            //    key =  Gen.genKey();
                            //    a.insertKey(Id, key);
                            //}
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
//Raspberry asking for a key check
                        case "userKey":
                            key = rW.read();
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
