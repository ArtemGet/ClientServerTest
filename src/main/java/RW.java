
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class RW implements Closeable {
    private final Socket socket;
    private final BufferedReader reader;
    private final BufferedWriter writer;
    public RW(String ip, int port) {
        try {
            this.socket = new Socket(ip, port);
            this.reader = createReader();
            this.writer = createWriter();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    public RW(ServerSocket server) throws IOException {

        this.socket = server.accept();
        this.reader = createReader();
        this.writer = createWriter();

    }
    private BufferedReader createReader() throws IOException {
        return new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }
    private BufferedWriter createWriter() throws IOException {
        return new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    public String readLine() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public  void writeLine(String message) {
        try {
            writer.write(message);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public int read() {
        try {
            return reader.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void write(int value) {
        try {
            writer.write(value);
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

<<<<<<< HEAD
    public Object[] readUserData()  { //num pass name lastname login email type
        try {
            Object[] userData = new Object[]{reader.read(),reader.read(),reader.readLine(),reader.readLine(),reader.readLine(), reader.readLine(), reader.readLine()};
=======
    public Object[] readPregnantData()  { //num pass name lastname login email
        try {
            Object[] userData = new Object[]{reader.read(),reader.read(),reader.readLine(),reader.readLine(),reader.readLine(), reader.readLine()};
>>>>>>> 3815a0a9139e7fdb126b4a5725dfad6f9ed0664e
            return userData;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
<<<<<<< HEAD
    public  void writeUserData(Object[] pregnantData) { //id pass userKey name lastname login email type
=======
    public  void writePregnantData(Object[] pregnantData) { //id pass userKey name lastname login email
>>>>>>> 3815a0a9139e7fdb126b4a5725dfad6f9ed0664e
        try {
            for (int a = 0; a <= 2; a++) {
                writer.write((int)pregnantData[a]);
                writer.flush();
            }
            for (int i = 3; i <= pregnantData.length-1; i++) {
                writer.write((String) pregnantData[i]);
                writer.newLine();
                writer.flush();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
<<<<<<< HEAD
    public void writeHelpData(String[] helpData) {
        for (String a: helpData
             ) {
            try {
                writer.write(a);
                writer.newLine();
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public Object[] readVerificationInf() {// key number name lastname type
        try {
            Object[] userData = new Object[] {reader.read(), reader.read(), reader.readLine(),reader.readLine(), reader.readLine()};
            return userData;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public String[] readHelpRequest() {//login,name, lastname, station in, station out, время, comment
        try {
            String[] helpData = new String[7];
            for (String a: helpData
                 ) {
                a = reader.readLine();
            }
            return helpData;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
=======
>>>>>>> 3815a0a9139e7fdb126b4a5725dfad6f9ed0664e
    @Override
    public void close() throws IOException {
        writer.close();
        reader.close();
        socket.close();
    }
}
