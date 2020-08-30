import java.io.Closeable;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class DBAccess implements Closeable {
    private static final String URL = "jdbc:mysql://localhost:3306/mydbtest?useUnicode=true&useSSL=true&useJDBCCompliantTimezoneShift=true" +
            "&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private Connection connection;
    private Statement statement;

    public DBAccess() {
        try {
            this.connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
            this.statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
//test method
    public void printDB() {
        ResultSet rs = null;
        try {
            rs = statement.executeQuery("SELECT * FROM userdata ");
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String desc = rs.getString(3);
                System.out.print(id + " ");
                System.out.print(name + " ");
                System.out.println(desc);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

//for verification on pregnant clien
    public int checkKey(int userid) {

        int key = 0;

        try {
            ResultSet rs = statement.executeQuery("SELECT userkey FROM userdata WHERE id =" + userid);
            while (rs.next()) {
                key = rs.getInt("userkey");
            }
            return key;
        } catch (SQLException e) {
            return 0;
        }
    }
    public void insertKey(int id, int key) {
        try {
            statement.executeUpdate("UPDATE userdata SET userkey=" + key + " where id=" + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//for registration on pregnant client
    public void setUserData( String type, String name, int pass) {
        try {
            statement.execute("INSERT INTO userdata ( type, name, pass) VALUES (" + "'"+ type + "'" + "," + "'" + name+ "'" + "," + pass + ");");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public int getId(String type, String name, int pass) {
        int id = 0;
        try {
           ResultSet rs = statement.executeQuery("SELECT id FROM userdata WHERE type =" + "'" + type + "' AND " + "name=" + "'" + name + "' AND " + "pass="  + pass );
        while (rs.next()) {
            id = rs.getInt("id");
            return id;
        }
        } catch (SQLException e) {
            //return 0;
        }
        return id;
    }

//for verification client (need to be tested)
    public ArrayList<String> getUnverifiedData() {
        ArrayList<String> userData = new ArrayList<>();
        try {
            ResultSet rs = statement.executeQuery("SELECT type, name FROM userdata WHERE userkey=0" );
            while (rs.next()) {
                    userData.add( rs.getString("type") + " " + rs.getString("name"));
            }
            return userData;
        } catch (SQLException e){
            userData.clear();
            userData.add("");
            return userData;
        }
    }
    public ArrayList<Integer> getUnverifiedId() {
        ArrayList<Integer> id = new ArrayList<>();
        try {
            ResultSet rs = statement.executeQuery("SELECT  id FROM userdata WHERE userkey=0 " );
            while (rs.next() ) {
                id.add(rs.getInt("id"));
            }
            return id;
        } catch (SQLException e){
            id.clear();
            id.set(0, 404);
            return id;
        }
    }



    @Override
    public void close() throws IOException {
        try {
            connection.close();
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
