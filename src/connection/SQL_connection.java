package connection;

import java.sql.*;

public class SQL_connection {

    public Connection getConnection(String url, String user, String pass, boolean closeConnection){
        Connection database = null;
        try {
            database = DriverManager.getConnection(url, user, pass);

            System.out.println("Database "+ database.getMetaData().getUserName()+" connected.");


        } catch(SQLException ex){
            System.out.println("Error while linking database: "+ ex);
        } finally{
            if (closeConnection & database != null){
             closeConnection(database);
            }

        }
        return database;
    }

    private void closeConnection(Connection c){
        try {
            assert c != null;
            c.close();
            System.out.println("Connection closed.");
        } catch (SQLException ex) {
            System.out.println("Error while closing connection: " + ex);;
        }

    }


    public void selectUser(Connection c, String username){
        System.out.println("Recovering user...");
        String query = "SELECT username, password FROM users WHERE username = ?";

        try (PreparedStatement ps = c.prepareStatement(query)){
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            listUsers(rs);

        } catch(SQLException ex){
            System.out.println("Error while recovering users.");
        }



    }

    private void listUsers(ResultSet rs) {
        try {
            while (rs.next())
                System.out.println("User: "+ rs.getString("username")
                + "\nPassword: " + rs.getString("password"));

        } catch(Exception ex){
            System.out.println("Error while listing users.");
        }

    }

}
