

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
                                //fix multi paper registration
                                a = new DBAccess();
                                userData = rW.readPregnantData();
                                boolean paperCheck = a.checkPregnantPaper(userData);
                                boolean loginCheck = a.checkLoginExists(userData);
                                boolean paperRegistered = a.checkPaperRegistered(userData);
                                System.out.println(paperCheck);
                                System.out.println(loginCheck);
                                System.out.println(paperRegistered);
                                key = genKey();

                                if (paperCheck && !paperRegistered && !loginCheck && a.getId(userData) == 0) {
                                    a.setPregnantData(userData, key);
                                    try {
                                        MailSender.sendMail((String) userData[5], key);
                                    } catch (MessagingException e) {
                                    }
                                    rW.writeLine("wait");
                                }
                                else if (paperCheck && !paperRegistered && loginCheck && a.getId(userData) != 0 ) {
                                    if (!a.checkVerified(a.getId(userData))) {
                                        try {
                                            MailSender.sendMail((String)userData[5], key);
                                        } catch (MessagingException e) {
                                        }
                                        a.resetPregnantData(userData, key);
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
                                key = rW.read();
                               int number = rW.read();
                               String  name = rW.readLine();
                               String lastName = rW.readLine();

                                a = new DBAccess();
                                if (a.checkKey(key)) {
                                    a.registerPregnantPaper(number,name,lastName);
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
                                rW.writePregnantData(a.getPregnantData(login,pass));
                                break;
                            case "passRecover":
                                a = new DBAccess();
                                 login = rW.readLine();
                                 String email = rW.readLine();
                                 pass = a.passRecover(login,email);
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
                                a = new DBAccess();
                                while(true) {
                                Id = rW.read();
                                key = a.getKey(Id);
                                if (key != 0) {
                                    rW.writeLine("On");
                                }
                                else if (key == 0) {
                                    rW.writeLine("Off");
                                }}
                        }
                    }).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static int genKey(){
          int s = -1;
          int p = 499;
          int w = 999;
          int Id = 0;
        return (int)(Math.random()*8999+1000);
    }
}



