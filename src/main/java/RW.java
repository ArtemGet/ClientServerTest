
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class RW implements Closeable {
    private final Socket socket;
    private final BufferedReader reader;
    private final BufferedWriter writer;
    //Client constructor(its for my debugging and tests)
    public RW(String ip, int port) {
        try {
            this.socket = new Socket(ip, port);
            this.reader = createReader();
            this.writer = createWriter();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //Server constructor
    public RW(ServerSocket server) throws IOException {

        this.socket = server.accept();
        this.reader = createReader();
        this.writer = createWriter();

    }

    //Here i create BReader(InputStream)
    private BufferedReader createReader() throws IOException {
        return new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }
    //OutputStream
    private BufferedWriter createWriter() throws IOException {
        return new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    //Reading/writing a String line
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
    //Reading/Writing an int value
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

    //Writing userRegData as a consequence of three Strings
    public  void writeUserData(String userType, String FIO, String password) {
        try {
            writer.write(userType);
            writer.newLine();
            writer.flush();
            writer.write(FIO);
            writer.newLine();
            writer.flush();
            writer.write(password);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //Reading userRegData as a String[]
    public String[] readUserData()  {
        try {
            String[] userData = new String[]{reader.readLine(), reader.readLine(), reader.readLine()};
            return userData;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //Reading Login, MetroStation and wheelChair invalid's commentary as a String[].
    public String[] readHelp() {
        try {
            String[] userData = new String[]{reader.readLine(), reader.readLine(), reader.readLine()};
            return userData;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override //close all streams
    public void close() throws IOException {
        writer.close();
        reader.close();
        socket.close();
    }
}
