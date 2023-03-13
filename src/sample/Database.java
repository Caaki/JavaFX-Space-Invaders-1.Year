package sample;

import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Database {
    private static Connection con = null;
    private static String url = "jdbc:mysql://localhost/projekat";
    private static String conusername = "root";
    private static String conpassword = "";


    //==========================Uzimanje user iz baze=========================
    public static ConObjekat getPlayer(String username2,String password2) {

        User p = new User();
        ConObjekat c = new ConObjekat(p);

        try {
            con = DriverManager.getConnection(url, conusername, conpassword);
            String query = "SELECT * FROM users WHERE username = '" + username2 + "' and password = '" + password2 + "';";
            Statement st = (Statement) con.createStatement();
            ResultSet rs = st.executeQuery(query);

            if (rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");
                int lastscore = rs.getInt("lastscore");
                int highScore = rs.getInt("highscore");
                int ID = rs.getInt("ID");

                User korisnik = new User(username, password, lastscore, highScore, ID);

                c.setUser(korisnik);
                c.setException(null);
            }
            else {
                c.setUser(null);
            }


            st.close();
            con.close();

        } catch (SQLException e) {
            c.setException(e);
            c.setUser(null);

        }
        return c;
    }

    //==========================Kreiranje korisnika=========================
    public static boolean addUser(String username, String password){

        try {
            con = DriverManager.getConnection(url,conusername,conpassword);
            PreparedStatement st = con.prepareStatement("INSERT INTO users" +
                    "(username,password,lastscore,highscore, admin)"
                    + " VALUES( ? ,  ? ,  ? , ?,?)");

            st.setString(1,username);
            st.setString(2,password);
            st.setInt(3,0);
            st.setInt(4,0);
            st.setBoolean(5,false);
            st.execute();
            con.close();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }

    }

    //==========================Menjanje nagon igre=========================
    public static void update(User u){

        try {
            con = DriverManager.getConnection(url,conusername,conpassword);
            PreparedStatement st = con.prepareStatement("UPDATE users SET lastscore = " +u.getScore()+", highscore = " + u.getHighScore()+
                    "WHERE username = '"+u.getUsername()+"'");

            st.execute();
            con.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();

        }
    }

    //==========================Vraca sve korisnike=========================
    public static ConList allUsers(){
        ConList conList = new ConList();
        List<User> povratnaLista = new ArrayList<>();

        try {
            con = DriverManager.getConnection(url, conusername, conpassword);
            String query = "SELECT * FROM users ORDER BY highscore DESC;";
            Statement st = (Statement) con.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");
                int lastscore = rs.getInt("lastscore");
                int highScore = rs.getInt("highscore");
                int ID = rs.getInt("ID");

                povratnaLista.add(new User(username, password, lastscore, highScore, ID));

            }

            conList.setUsers(povratnaLista);

        } catch (SQLException throwables) {
            conList.setException(throwables);
            conList.setException(throwables);
        }
        System.out.println(conList.getUsers());
        return conList;
    }
}
