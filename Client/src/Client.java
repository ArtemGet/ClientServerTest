import newPack.ReadWr;

import java.io.*;



public class Client {

    public static void main(String[] args) {
        try (ReadWr readWr = new ReadWr("192.168.0.110",9000))
        {
           readWr.writeUserData("pregnant","Gay Gaivich Gayev","110");

            System.out.println(readWr.readId());
            int gay = readWr.readId();
            System.out.println(gay);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
