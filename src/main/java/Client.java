

import java.io.*;



public class Client {

    public static void main(String[] args) {
        try (RW rW = new RW("192.168.0.110",9000))
        {
            rW.writeLine("userRegData");
            rW.writeUserData("pregnant","Gay Gaivich Gayev","110");

            int Id = rW.read();

            System.out.println(Id);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
