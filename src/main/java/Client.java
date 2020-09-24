

import java.io.*;



public class Client {

    public static void main(String[] args) {
        try (RW rW = new RW("192.168.0.110",9000))
        {
            rW.writeLine("helpGet");
            Thread.sleep(5000);
            rW.writeLine("accepted");

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
