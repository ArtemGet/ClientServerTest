import newPack.ReadWr;

import java.io.*;



public class Client {

    public static void main(String[] args) {
        try (ReadWr rW = new ReadWr("192.168.0.101",9000))
        {
           rW.writeUserData("pregnant","Gay Gaivich Gayev","110");



        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
