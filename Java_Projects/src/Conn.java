package src;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Conn {
    Connection c;
    Statement s;
    // String filePath = "/Users/hamzamohammed/Documents/Java_Projects/src/.gitignore/config.properties";
    // Properties config = loadConfig();
    // final String URL = config.getProperty("url");
    // final String USER = config.getProperty("user");
    // final String PASS = config.getProperty("password");

    public Conn() {
        try {
            // Class.forName(com.mysql.cj.jbdc.Driver);
            c = DriverManager.getConnection("jdbc:mysql:///ExoticRentalsAcc", "root", "Hh426492!15987412");
            s = c.createStatement();
        }

        catch (Exception e) {
            System.out.println(e);
        }
    }

    // private Properties loadConfig() {
    //     Properties config = new Properties();
    //     try (InputStream input = new FileInputStream("/Users/hamzamohammed/Documents/Java_Projects/src/.gitignore/config.properties")) {
    //         config.load(input);
    //     } catch (IOException e) {
    //         throw new RuntimeException("Error reading config.properties", e);
    //     }
    //     return config;
    // }


    public void insertData(String firstName, String lastName, String employeeID, String password) {
        try {
            String sql = "INSERT INTO accounts (first_name, last_name, employee_id, password) VALUES ('" +
                    firstName + "', '" + lastName + "', '" + employeeID + "', '" + password + "')";
            s.executeUpdate(sql);

            System.out.println("Data Inserted Successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeData(String firstName, String lastName, String employeeID, String password) {
        try {
            // Use DELETE SQL statement with the correct table name and conditions
            String sql = "DELETE FROM accounts WHERE first_name = '" + firstName + "' AND last_name = '" + lastName +
                         "' AND employee_id = '" + employeeID + "' AND password = '" + password + "'";

            // Execute the SQL statement
            int rowsAffected = s.executeUpdate(sql);

            if (rowsAffected > 0) {
                System.out.println("Data removed successfully");
            } else {
                System.out.println("No matching data found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertCustomerData(String firstName, String lastName, String phoneNumber, String cardInfo, String cvv, String expDate,
                               String vehicleType, String fromDate, String toDate) {
    try {
        String sql = "INSERT INTO rentalsCustomersInfo (f_name, l_name, phone_number, credit_card, cvv, expirationDate, vehicle_type, From_date, To_date) " +
                "VALUES ('" + firstName + "', '" + lastName + "', '" + phoneNumber + "', '" + cardInfo + "', '" +
                cvv + "', '" + expDate + "', '" + vehicleType + "', '" + fromDate + "', '" + toDate + "')";
        s.executeUpdate(sql);

        System.out.println("Data Inserted Successfully");
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

public void removeCustomerData(String firstName, String lastName, String phoneNumber, String cardInfo, String cvv, String expDate,
                               String vehicleType, String fromDate, String toDate) {
    try {
        // Use DELETE SQL statement with the correct table name and conditions
        String sql = "DELETE FROM rentalsCustomersInfo WHERE f_name = '" + firstName + "' AND l_name = '" + lastName +
                "' AND phone_number = '" + phoneNumber + "' AND credit_card = '" + cardInfo + "' AND cvv = '" + cvv +
                "' AND expirationDate = '" + expDate + "' AND vehicle_type = '" + vehicleType + "' AND From_date = '" +
                fromDate + "' AND To_date = '" + toDate + "'";

        // Execute the SQL statement
        int rowsAffected = s.executeUpdate(sql);

        if (rowsAffected > 0) {
            System.out.println("Data removed successfully");
        } else {
            System.out.println("No matching data found");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    public boolean employeeExists(String employee) {
        try {
            String sql = "SELECT * FROM accounts WHERE employee_id = '" + employee + "'";
            var result = s.executeQuery(sql);
            return result.next();
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean passwordExists(String employee, String password) {
        try {
            String sql = "SELECT * FROM accounts WHERE employee_id = '" + employee + "' AND password = '" + password + "'";
            var result = s.executeQuery(sql);
            return result.next();
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
