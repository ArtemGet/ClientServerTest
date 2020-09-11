

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
    private PreparedStatement prStatement;

    private static final String GET_ID = "SELECT id FROM userdata WHERE ? AND ?";

    public DBAccess() {
        try {
            this.connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
            this.statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getKey(int id) {
        int key = 0;
        try {
            ResultSet rs = statement.executeQuery("SELECT userkey FROM userdata WHERE id =" + id);
            while (rs.next()) {
                key = rs.getInt("userkey");
            }
            return key;
        } catch (SQLException e) {
            return 0;
        }
    }
    public int getId(Object[] userData) {
        int id = 0;
        int pass = (int)userData[1];
        String login = (String)userData[4];
        try {
            //prStatement = connection.prepareStatement(GET_ID);
            //prStatement.setString(1,login);
            //prStatement.setInt(2,pass);
            //ResultSet rs = prStatement.executeQuery();
           ResultSet rs = statement.executeQuery("SELECT id FROM userdata WHERE " + "login=" + "'" + login + "' AND " + "pass="  + pass);
        while (rs.next()) {
            id = rs.getInt("id");
        }
            return id;
        } catch (SQLException e) {
        }
        return id;
    }
    public int getId(int key) {

        int id = 0;
        try {
            ResultSet rs = statement.executeQuery("SELECT id FROM userdata WHERE userkey =" + key);
            while (rs.next()) {
                id = rs.getInt("id");
                return id;
            }
        } catch (SQLException e) {
        }
        return id;
    }
    public Object[] getPregnantData(String login, int pass) {
        //дописать проверку на null
        Object[] data = new Object[]{0, 0, 0, "null", "null","null", "null"};
        try {
            ResultSet rs = statement.executeQuery("SELECT id,name,lastname, login, pass, email, userkey FROM userdata WHERE login =" + "'" + login + "'" +" AND " + "pass="  + pass  );
            while(rs.next()) {
                data = new Object[]{rs.getInt("id"),rs.getInt("pass"), rs.getInt("userkey"), rs.getString("name"), rs.getString("lastname"),
                        rs.getString("login"), rs.getString("email")};
            }
            if ((int)data[0] == 0) {
                data = new Object[]{0, 0, 0, "null", "null","null", "null"};
            }
            return data;
        } catch (SQLException throwable) {
            return data;
        }
    }

    public boolean checkKey(int key) {
        int checkKey = 0;
        try {
            ResultSet rs = statement.executeQuery("SELECT userkey FROM userdata WHERE userkey =" + key);
            while (rs.next()) {
                checkKey = rs.getInt("userkey");
            }
            if (key == checkKey) {
                return true;
            }
            else {
                return false;
            }
        } catch (SQLException e) {
            return false;
        }
    }
    public boolean checkPaperRegistered(Object[] userData) {
        int num = (int) userData[0];
        String name = (String) userData[2];
        String lastName = (String) userData[3];
        int registered = 0;
        try {
            ResultSet rs = statement.executeQuery("SELECT registered FROM paper WHERE number =" + num + " AND " +
                    "name=" + "'" + name + "' AND " + "lastname=" + "'" + lastName + "'");
            while (rs.next()) {
                registered = rs.getInt("registered");
            }
            if (registered == 1) {
                return true;
            }
            else {
                return false;
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return false;
    }
    public boolean checkPregnantPaper(Object[] userData) {
        // num pass name lastname login email
        //needs num, String name, String lastname
        int num = (int)userData[0];
        String name = (String)userData[2];
        String lastName = (String)userData[3];
        int number = 0;
        try {
            ResultSet rs = statement.executeQuery("SELECT number FROM paper WHERE number =" + num + " AND " + "name=" + "'" + name + "' AND " + "lastname="  + "'" + lastName + "'");
            while (rs.next()) {
                number = rs.getInt("number");
            }
        if (number == num) {
            return true;
        }
        else {
            return false;
        }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return true;
    }
    public boolean checkLoginExists(Object[] userData) {
        String login = (String)userData[4];
        String logincheck = "";
        ResultSet rss = null;
        int c = 0;
        try {
            rss = statement.executeQuery("SELECT login FROM userdata WHERE login= '" + login + "'");
            while (rss.next()) {
                c++;
                logincheck = rss.getString("login");
            }
            if (login.equals(logincheck)) {
                return true;
            }
            else {
                return false;
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return false;
    }
    public boolean checkVerified(int Id) {
        int registered = 0;
        try {
            ResultSet rs = statement.executeQuery("SELECT registered FROM userdata WHERE id= " + Id);
            while (rs.next()) {
                registered = rs.getInt("registered");
                System.out.println(registered);
            }
            if (registered == 1) {
                return true;
            }
            else {
                return false;
            }
        } catch (SQLException throwables) {
            return false;
        }
    }
    public boolean checkVerified(String login,int pass) {
        int registered = 0;
        try {
            ResultSet rs = statement.executeQuery("SELECT registered FROM userdata WHERE login= " + "'" + login + "'" + " AND " + "pass= " + pass);
            while (rs.next()) {
                registered = rs.getInt("registered");
                System.out.println(registered);
            }
            if (registered == 1) {
                return true;
            }
            else {
                return false;
            }
        } catch (SQLException throwables) {
            return false;
        }
    }

    public void registerPregnantPaper(int num, String name, String lastName) {
        try {
            statement.executeUpdate("UPDATE paper SET registered=1 WHERE number =" + num + " AND " + "name=" + "'" + name + "' AND " + "lastname="  + "'" + lastName + "'");
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
    public void insertKey(int id, int key) {
        try {
            statement.executeUpdate("UPDATE userdata SET userkey=" + key + " where id=" + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void setPregnantData(Object[] userData, int key) {
        //num pass name lastname login email
        int pass = (int)userData[1];
        String name = (String)userData[2];
        String lastName = (String) userData[3];
        String login = (String)userData[4];
        String email = (String)userData[5];
        try {
            statement.execute("INSERT INTO userdata (name,lastname ,login, pass,email, userkey) VALUES (" + "'" + name+ "'" + "," + "'" + lastName+ "'" + "," + "'" + login+ "'" + "," + pass + ","
                    + "'" + email+ "'" + ","+ key + ");");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void resetPregnantData(Object[] userData, int key) {
        int pass = (int)userData[1];
        String name = (String)userData[2];
        String lastName = (String) userData[3];
        String login = (String)userData[4];
        String email = (String)userData[5];
        try {
            statement.executeUpdate("UPDATE userdata SET name=" + "'" + name + "'" + "," +" lastname=" + "'" + lastName + "'" + "," + " login="  + "'" + login + "'"
                            + "," + " pass=" + pass + "," +  " email=" + "'" + email+ "'" + "," +  " userkey=" + key + " WHERE login=" + "'" + login + "'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int passRecover(String login, String email) {
        int pass = 0;
        try {
            ResultSet rs = statement.executeQuery("SELECT pass FROM userdata WHERE login="  + "'" + login + "'" +" AND " + "email= " + "'" + email + "'");
            while (rs.next()) {
                 pass = rs.getInt("pass");
            }
            return pass;
        } catch (SQLException throwable) {
            return pass;
        }
    }
    public int resetPass(int Id, int pass){
        try {
            statement.executeUpdate("UPDATE userdata SET pass= " + pass + " WHERE id= " + Id);
            ResultSet rs = statement.executeQuery("SELECT pass FROM userdata WHERE id= " + Id);
            while (rs.next()){
                pass = rs.getInt("pass");
            }
            return  pass;
        } catch (SQLException throwable) {
            return pass;
        }
    }

    public void verify(int Id){
        try {
            int update = statement.executeUpdate("UPDATE userdata SET registered= " + 1 + " WHERE id= " + Id + ";");
        } catch (SQLException throwables) {
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
