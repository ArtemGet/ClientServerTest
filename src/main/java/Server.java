

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
                        int key = 0;
                        DBAccess a;
                        Object[] userData;
                        String gay = rW.readLine();
                        switch (gay) {
//Registration Android Client
                            case "userRegData" :
                                a = new DBAccess();
                                 userData = rW.readUserData();
                                System.out.println("connected");
                                key = Gen.genKey();
                                //добавить проверку регистрации String type, String name, int pass, int key

                                System.out.println((String)userData[0]);
                                System.out.println((String)userData[1]);
                                System.out.println((int)userData[4]);

                                if (a.getId((String)userData[0], (String)userData[1], (int)userData[5]) == 0) {
                                    a.setUserData((String) userData[0], (String) userData[1], (String) userData[2],(String) userData[3], (int) userData[4], key);
                                    //System.out.println(a.getId((String) userData[0],(String) userData[1],(int)userData[2]));
                                    Id = a.getId((String) userData[0], (String) userData[1], (int) userData[3]);
                                    System.out.println(Id);
                                    key = Gen.genKey();

                                    rW.write(Id);
                                    rW.write(key);
                                }
                                else {
                                    rW.write(0);
                                    rW.write(0);
                                }
                                break;
                            case "loginPregnant":
                                System.out.println("connected");
                                a = new DBAccess();
                                userData = rW.readPregnantData();
                                boolean check = a.checkPregnantPaper((int)userData[0],(String) userData[1], (String) userData[2]);
                                if(check && (a.getId("pregnant", (String)userData[1], (int)userData[3]) == 0)) {
                                    key = Gen.genKey();
                                    a.setPregnantData((String)userData[1], (String)userData[2],(int) userData[3], key );
                                    Id = a.getId("pregnant",(String) userData[1],(int)userData[3]);
                                    key = Gen.genKey();

                                    System.out.println(Id);
                                    System.out.println(key);
                                    rW.write(Id);
                                    rW.write(key);
                                }
                                else {
                                    rW.write(0);
                                    rW.write(0);
                                }
                                break;
                            case "login":
                                a = new DBAccess();
                                Id = rW.read();
                                key = rW.read();
                                Object[] Userdata = a.getUserData(Id,key);
                                    rW.write((int)Userdata[0]);
                                    rW.writeUserData((String)Userdata[1],(String)Userdata[2],(String)Userdata[3],
                                    (String)Userdata[4],(int)Userdata[5],(int)Userdata[6]);
                                break;
//Adding key to Android Client after verification
                           /* case "userId":
                                Id = rW.read();
                                a = new DBAccess();
                                key = a.getKey(Id);
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
                                break; */
                            //Verify Android Client
                            /*case "adminId":
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
                                break;*/
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
                                a = new DBAccess();
                                Id = rW.read();
                                key = a.getKey(Id);
                                if (key != 0) {
                                    rW.writeLine("On");
                                }
                                else if (key == 0) {
                                    rW.writeLine("Off");
                                }
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
