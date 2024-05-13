package com.example.aj;
import java.sql.*;

public class DatabaseManager {
    public static DatabaseManager ct;

    private Connection cnx;

    String url = "jdbc:mysql://localhost:3306/aj";

    String user = "root";
    String pwd = "root";

    private DatabaseManager() {
        try {
            cnx = DriverManager.getConnection(url,user,pwd);
            System.out.println("Cnx BD etablie avec succes");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static DatabaseManager getInstance(){
        if(ct ==null)
            ct= new DatabaseManager();
        return ct;
    }

    public Connection getCnx() {
        return cnx;
    }
   
}

