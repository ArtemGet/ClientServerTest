

import javax.mail.MessagingException;
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
                        String input = rW.readLine();
                        int Id;
                        int key = 0;
                        String login;
                        int pass;
                        DBAccess a;
                        Object[] userData;


                        switch (input) {
                            case "regPregnant":
                                //добавить проверку на существующий логин
                                //сделать замену в бд, а не добавление нового
                                System.out.println("regPregnant");
                                a = new DBAccess();
                                userData = rW.readPregnantData();
                                boolean check = a.checkPregnantPaper((int)userData[0],(String) userData[1], (String) userData[2]);
                                if(check && (a.getId("pregnant", (String)userData[1], (int)userData[4]) == 0)) {
                                    key = Gen.genKey();
                                    a.setPregnantData((String)userData[1], (String)userData[2],(String)userData[3],(int) userData[4], key, (String)userData[5]);
                                    Id = a.getId("pregnant",(String) userData[1],(int)userData[4]);

                                    try {
                                        MailSender.sendMail((String) userData[5], key);
                                    } catch (MessagingException e) {
                                    }

                                    rW.writeLine("wait");
                                }
                                else if (check && (a.getId("pregnant", (String)userData[1], (int)userData[4]) != 0) ) {
                                    if (!a.checkVerif(a.getId("pregnant", (String)userData[1], (int)userData[4]))) {
                                        try {
                                            MailSender.sendMail((String) userData[5], key);
                                        } catch (MessagingException e) {
                                        }
                                        a.setPregnantData((String)userData[1], (String)userData[2],(String)userData[3],(int) userData[4], key, (String)userData[5]);
                                        rW.writeLine("wait");
                                    }
                                    else {
                                        rW.writeLine("exist");
                                    }
                                }
                                else {
                                    rW.writeLine("wrong");
                                }
                                break;
                            case "verify":
                                System.out.println("verify");
                                key = rW.read();
                                System.out.println(key);
                                a = new DBAccess();
                                System.out.println(a.checkKey(key));
                                if (a.checkKey(key)) {
                                    a.verify(a.getId(key));
                                    System.out.println(a.getId(key));
                                    rW.write(a.getId(key));
                                }
                                else {
                                    rW.write(0);
                                }
                                break;
                            case "login":
                                a = new DBAccess();
                                login = rW.readLine();
                                 pass = rW.read();
                                 if (a.checkVerif(login, pass)) {
                                     Object[] Userdata = a.getUserData(login,pass);
                                     rW.write((int)Userdata[0]);
                                     System.out.println((int)Userdata[7]);
                                     rW.writeUserData((String)Userdata[1],(String)Userdata[2],(String)Userdata[3],
                                             (String)Userdata[4],(int)Userdata[5],(String) Userdata[6], (int)Userdata[7]);
                                 }
                                 else {
                                     rW.write(0);
                                     rW.writeUserData("null","null","null",
                                             "null",0,"null", 0);
                                 }
                                break;
                            case "passRecover":
                                //добавить проверку на верификацию
                                System.out.println("passrecover");
                                a = new DBAccess();
                                 login = rW.readLine();
                                 String email = rW.readLine();
                                 pass = a.passRecover(login,email);

                                System.out.println(pass);
                                 if(pass != 0) {
                                     try {
                                         MailSender.sendMail(email,"PasswordRecovery",String.valueOf(pass));
                                     } catch (MessagingException e) {
                                         e.printStackTrace();
                                     }
                                 }
                                break;
                            case "passChange":
                                a = new DBAccess();
                                Id = rW.read();
                               pass = rW.read();
                               rW.write(a.resetPass(Id,pass));
                                break;
                            case "userKey":
                                //pootis
                                System.out.println("userKey");
                                a = new DBAccess();
                                while(true) {
                                Id = rW.read();
                                    System.out.println(Id);
                                key = a.getKey(Id);
                                if (key != 0) {
                                    rW.writeLine("On");
                                    System.out.println("On");
                                }
                                else if (key == 0) {
                                    rW.writeLine("Off");
                                    System.out.println("Off");
                                }}
                        }
                    }).start();
            }
        }

        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
