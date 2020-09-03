

import java.io.*;
import java.net.ServerSocket;
import java.util.ArrayList;


public class Server {
    public static void main(String[] args) {

        try (ServerSocket server = new ServerSocket(9000)) {
            System.out.println("Start");
            while (true) {

                    RW rW = new RW(server);
                    new Thread(()->{
                        int Id;
                        int key;
                        DBAccess a;
                       String gay = rW.readLine();
                        switch (gay) {
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
                                Id = rW.read();
                                a = new DBAccess();
                                key = a.checkKey(Id);
                                if (key != 0) {

                                    rW.write(key);
                                }
                                else {
                                    try {
                                        rW.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                break;
                            //Verify Android Client
                            case "adminId":
                                System.out.println("connected");
                                a = new DBAccess();
                                ArrayList<Integer> ID = a.getUnverifiedId();
                                ArrayList<String> DATA =  a.getUnverifiedData();
                                rW.writeUnverified(ID,DATA);
                                String respond = rW.readLine();
                                System.out.println(respond);

                                //respond = "disconnect";
                                while( respond.equals("disconnect") != true) {
                                    if (respond.equals("takeId")) {
                                        System.out.println("взял");
                                        ArrayList<Integer> verifiedId = rW.readVerifiedId();
                                        for (int b : verifiedId
                                        ) {
                                            a.insertKey(b, Gen.genKey());
                                        }
                                    }
                                    else if (respond.equals("refresh")) {
                                        System.out.println("рефрешнул");
                                        ID = a.getUnverifiedId();
                                        DATA =  a.getUnverifiedData();
                                        rW.writeUnverified(ID,DATA);
                                    }
                                    respond = rW.readLine();

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

                    }).start();



            }
        }

        catch (IOException e) {
            throw new RuntimeException(e);
        }





    }
}
