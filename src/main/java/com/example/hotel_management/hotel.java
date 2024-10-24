package com.example.hotel_management;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class hotel {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        String url = "jdbc:mysql://localhost:3306/hotel_management";
        String user = "root";
        String password = "Shitlamaa@8989";
       try{
           Class.forName("com.mysql.jdbc.Driver");

       }catch(ClassNotFoundException e){
           System.out.println(e.getMessage());
       }
       try{
           Connection connection= DriverManager.getConnection(url,user,password);
           System.out.println("Connection successfully...");
           System.out.println(connection);

       }catch(SQLException e){
           System.out.println(e.getMessage());
       }

    }

    }
