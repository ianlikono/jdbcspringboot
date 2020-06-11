package com.example.springboot;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConnectionController {
    @RequestMapping("/connection")
    public String index() {
        //manually cresting connection string
        String connStr3 = "jdbc:sqlserver://testdb.database.windows.net:1433;database=testdb;authentication=ActiveDirectoryMSI;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
        String results = "";
        try (Connection connection = DriverManager.getConnection(connStr3);) {
            // Code here.
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT TOP 10 * FROM Persons");
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
        }


        //manually cresting connection string


        //using SQLServerDataSource to create connection string
        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setServerName("testdb.database.windows.net"); // Replace with your server name
        ds.setDatabaseName("testdb"); // Replace with your database name
        ds.setAuthentication("ActiveDirectoryMSI");

        try (Connection connection = ds.getConnection();
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT TOP 10 * FROM Persons")) {

            if (rs.next()) {
                System.out.println("You have successfully logged on as: " + rs.getString(1));
                results = "worked";
            }
        } catch (SQLException e) {
            results = e.toString();
        }
        return results;
    }

}