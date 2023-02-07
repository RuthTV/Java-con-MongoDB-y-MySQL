package com.mySQL.mySQL;


import java.sql.*;

public class ConnectionMySQL {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/midtermPoyect";
        String username = "ironhacker";
        String password = "Ir0nh4ck3r!";

        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from student");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}