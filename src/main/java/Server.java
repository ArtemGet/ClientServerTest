

import javax.mail.MessagingException;
import java.io.*;
import java.net.ServerSocket;
import java.util.ArrayList;


public class Server {
    public static void main(String[] args) {

        try (ServerSocket server = new ServerSocket(900)) {
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
                    boolean loginCheck;
                    String[] helpData;
                    a = new DBAccess();

                    switch (input) {
                        case "userRegData":
                            a = new DBAccess();
                            userData = rW.readUserData();
                            loginCheck = a.checkLoginExists(userData);
                            key = genKey();
                            if (!loginCheck) {
                                a.setUserData(userData,key);
                                try {
                                    MailSender.sendMail((String)userData[5], key);
                                    rW.writeLine("wait");
                                } catch (MessagingException e) {
                                    e.printStackTrace();
                                }
                            }
                            else if (loginCheck && !a.checkVerified(a.getId(userData))) {
                                a.resetPregnantData(userData, key);
                                try {
                                    MailSender.sendMail((String)userData[5], key);
                                    rW.writeLine("wait");
                                } catch (MessagingException e) {
                                    e.printStackTrace();
                                }
                            }
                            else {
                                rW.writeLine("wrong");
                            }
                            break;
                        case "regPregnant":
                            a = new DBAccess();
                            userData = rW.readUserData();
                            boolean paperCheck = a.checkPregnantPaper(userData);
                            loginCheck = a.checkLoginExists(userData);
                            boolean paperRegistered = a.checkPaperRegistered(userData);
                            key = genKey();

                            if (paperCheck && !paperRegistered && !loginCheck && a.getId(userData) == 0) {
                                a.setUserData(userData, key);
                                try {
                                    MailSender.sendMail((String) userData[5], key);
                                } catch (MessagingException e) {
                                }
                                rW.writeLine("wait");
                            }
                            else if (paperCheck && !paperRegistered && loginCheck && a.getId(userData) != 0 ) {
                                System.out.println( a.getId(userData));
                                if (!a.checkVerified(a.getId(userData))) {
                                    try {
                                        MailSender.sendMail((String)userData[5], key);
                                    } catch (MessagingException e) {
                                    }
                                    a.resetPregnantData(userData, key);
                                    rW.writeLine("wait");
                                }
                            }
                            else {
                                rW.writeLine("wrong");
                            }
                            break;
                        case "verify":
                            userData = rW.readVerificationInf();
// key num name lastname type
                            a = new DBAccess();
                            System.out.println((String) userData[4]);
                            if (a.checkKey((int)userData[0]) && ((String) userData[4]).equals("Беременная")) {
                                //Васе добавить в код pregnant
                                System.out.println("pr verif");
                                a.registerPregnantPaper((int)userData[1],(String)userData[2],(String)userData[3]);
                                a.verify(a.getId((int)userData[0]));
                                rW.write(a.getId((int)userData[0]));
                                System.out.println(a.getId((int)userData[0]));
                            }
                            else if (a.checkKey((int)userData[0]) && !((String) userData[4]).equals("Беременная")) {
                                System.out.println("user verif");
                                a.verify(a.getId((int)userData[0]));
                                rW.write(a.getId((int)userData[0]));
                                System.out.println(a.getId((int)userData[0]));

                            }
                            else {
                                rW.write(0);
                            }
                            break;
                        case "login":
                            a = new DBAccess();
                            login = rW.readLine();
                            pass = rW.read();
                            System.out.println("logged");
                            rW.writeUserData(a.getUserData(login,pass));
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
                            System.out.println("Raspberry connected");
                            a = new DBAccess();
                            while(true) {
                                Id = rW.read();
                                System.out.println(Id);
                                key = a.getKey(Id);
                                System.out.println(key);
                                if (key != 0) {
                                    rW.writeLine("On");
                                    System.out.println("On");
                                }
                                else if (key == 0) {
                                    rW.writeLine("Off");
                                    System.out.println("Off");
                                }}
                        case "helpRequest":
                            //login,name, lastname, station in, station out, время, comment
                            System.out.println("helpRequest");
                            a = new DBAccess();
                            helpData = rW.readHelpRequest();
                            a.insertHelpData(helpData);
                            for (String b: helpData
                            ) {
                                System.out.println(b);
                            }
                            boolean helpAccepted = a.getHelpAccepted(helpData[0]);
                            while (!helpAccepted) {
                                helpAccepted = a.getHelpAccepted(helpData[0]);
                            }
                            a.resetHelpAccepted(helpData[0]);
                            rW.writeLine("helpAccepted");
                            //тут чат
                            break;
                        case "helpGet":
                            a = new DBAccess();
                            helpData = a.getHelpData();
                            rW.writeHelpData(helpData);
                            String resp = rW.readLine();
                            if (resp.equals("accepted")) {
                                a.clearHelpData(helpData[7]);
                                a.setHelpAccepted(helpData[0]);
                            }

                            //тут открываем ламповый чатик
                            //закрывается при нажатии на кнопку сдать заказ
                            //отправляется логин инвалида
                            break;
                        case "helpWaitWheel":
                            //берес с бд логин
                            //если yes - wait
                            //если no - accept
                            break;
                        case "helpWaitSoc":
                            a = new DBAccess();
                            //while () {
                            // ArrayList<String> helpData = a.getHelpdata();
                            //добавить проверку на повторные запросы
                            // if (!helpData.get(0).equals("")) {
                            //     rW.writeHelpData(a.getHelpData());
                            // }
                            //  }
                            //break;
                    }
                }).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static int genKey(){
        return (int)(Math.random()*8999+1000);
    }
}



