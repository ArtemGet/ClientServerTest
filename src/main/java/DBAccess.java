import java.io.Closeable;
import java.io.IOException;
import java.sql.*;

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
//server checks if key is attached to given Id  (when user trying to connect after reg it recognizes do we have that user verified)
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

    public void setUserData(String type, String name, int pass) {
        try {
            statement.execute("INSERT INTO userdata ( type, name, pass) VALUES (" +"'"+ type + "'" + "," + "'" + name+ "'" + "," + pass + ");");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
//needs to be rewrited and tested
    public Object[][][] searchUnverif() {
        Object[][][] userData = new Object[][][];
        int id = 0;
        String type = "";
        String name = "";
        try {
            ResultSet rs = statement.executeQuery("SELECT id, type, name FROM userdata WHERE userkey= null");
            while (rs.next()) {
                for (Object a:userData) {
                  a = rs.getInt("id");

                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return
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
