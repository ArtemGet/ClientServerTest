
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

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
    public  void writeUserData(String userType, String name,String lastName, int password) {
        try {
            writer.write(userType);
            writer.newLine();
            writer.flush();
            writer.write(name);
            writer.newLine();
            writer.flush();
            writer.write(lastName);
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
    public Object[] readUserData()  {
        try {
            Object[] userData = new Object[]{reader.readLine(),reader.readLine(),reader.readLine(), reader.read()};
            return userData;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public Object[] readPregnantData()  {
        try {
            Object[] userData = new Object[]{reader.read(),reader.readLine(),reader.readLine(),reader.read()};
            return userData;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public  void writePregnantData(int number, String name,String lastName, int password) {
        try {
            writer.write(number);
            writer.newLine();
            writer.flush();
            writer.write(name);
            writer.newLine();
            writer.flush();
            writer.write(lastName);
            writer.newLine();
            writer.flush();
            writer.write(password);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //Reading Login, MetroStation and wheelChair invalid's commentary as a String[] (need to be tested)
    public String[] readHelp() {
        try {
            String[] userData = new String[]{reader.readLine(), reader.readLine(), reader.readLine()};
            return userData;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //parsing unverified ids and data(need to be tested)
    public void writeUnverified(ArrayList<Integer> id, ArrayList<String> data) {
        try {
            writer.write(id.size());
            writer.flush();
                for (int a : id
                ) {
                    writer.write(a);
                    writer.flush();
                }
                writer.write(data.size());
                writer.flush();
                for (String b:data
                ) {
                    writer.write(b);
                    writer.newLine();
                    writer.flush();
                }
            } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public ArrayList<Integer> readUnverifiedId() throws IOException {
        int num = reader.read();

        ArrayList<Integer> id = new ArrayList<>(num);
        for (int c = 0; c <num;c++) {
            id.add(c,reader.read());
        }
        return id;
    }
    public ArrayList<String> readUnverifiedData() throws IOException {
        int num = reader.read();
        ArrayList<String> data = new ArrayList<>(num);
        for (int c = 0; c <num;c++) {
            data.add(c,reader.readLine());
        }
        return data;
    }

    public void writeVerifiedId( ArrayList<Integer> Id) throws IOException {
        writer.write(Id.size());
        writer.flush();
        for (int a: Id
        ) {
            writer.write(a);
            writer.flush();
        }
    }
    public ArrayList<Integer> readVerifiedId()  {
        try {
            int size = reader.read();
            ArrayList<Integer> Id = new ArrayList<>(size);

            for (int c = 0; c < size; c++) {
                Id.add(reader.read());

            }
            return Id;
        } catch (IOException e) {
            disconnect();
            System.out.println("disconnected");
            return null;
        }
    }
    public void disconnect() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override //close all streams
    public void close() throws IOException {
        writer.close();
        reader.close();
        socket.close();
    }
}
