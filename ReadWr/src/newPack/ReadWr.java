package newPack;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ReadWr implements Closeable {
    private final Socket socket;
    private final BufferedReader reader;
    private final BufferedWriter writer;

    public ReadWr(String ip, int port) {
        try {
            this.socket = new Socket(ip, port);
            this.reader = createReader();
            this.writer = createWriter();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public ReadWr(ServerSocket server) {
        try {
            this.socket = server.accept();
            this.reader = createReader();
            this.writer = createWriter();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private BufferedReader createReader() throws IOException {
        return new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }
    private BufferedWriter createWriter() throws IOException {
        return new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
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
    public String readLine() {
         try {
             return reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() throws IOException {
        writer.close();
        reader.close();
        socket.close();
    }
}
