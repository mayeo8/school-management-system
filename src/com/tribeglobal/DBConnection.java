package com.tribeglobal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DBConnection {
    static Connection connection;
    public static Connection createDBConnection(){
        //connection strings
        String url = "jdbc:mysql://localhost:3306/school_management";
        String username = "root";
        String password = "couture";
        try {
            //creating the connection
            connection = DriverManager.getConnection(url,username,password);
            Statement statement = connection.createStatement();
        }catch (Exception e){
            e.printStackTrace();
        }
        //returning it
        return connection;
    }
}
