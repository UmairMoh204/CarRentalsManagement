package src;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import javax.swing.table.DefaultTableModel;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Conn {
    Connection c;
    Statement s;
    Properties config = loadConfig();
    final String URL = config.getProperty("url");
    final String USER = config.getProperty("user");
    final String PASS = config.getProperty("password");

    public Conn() {
        try {
            c = DriverManager.getConnection(URL, USER, PASS);
            s = c.createStatement();
        }
        catch (Exception e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private Properties loadConfig() {
        Properties config = new Properties();
        try (InputStream input = getClass().getResourceAsStream("config.properties")) {
            config.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Error reading config.properties", e);
        }
        return config;
    }


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
            String sql = "DELETE FROM accounts WHERE first_name = '" + firstName + "' AND last_name = '" + lastName +
                         "' AND employee_id = '" + employeeID + "' AND password = '" + password + "'";

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
        String sql = "DELETE FROM rentalsCustomersInfo WHERE f_name = '" + firstName + "' AND l_name = '" + lastName +
                "' AND phone_number = '" + phoneNumber + "' AND credit_card = '" + cardInfo + "' AND cvv = '" + cvv +
                "' AND expirationDate = '" + expDate + "' AND vehicle_type = '" + vehicleType + "' AND From_date = '" +
                fromDate + "' AND To_date = '" + toDate + "'";

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

    public ResultSet getDataFromDatabase() {
        try {
            Statement statement = c.createStatement();
            String query = "SELECT * FROM rentalsCustomersInfo";
            return statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean isCarAvailable(String selectedVehicle, String fromDate, String toDate) {
        try {
            String sql = "SELECT * FROM rentalsCustomersInfo " +
                     "WHERE vehicle_type = ? " +
                     "AND (From_date >= ? AND From_date <= ? OR To_date >= ? AND To_date <= ?)";
            try (PreparedStatement preparedStatement = c.prepareStatement(sql)) {
                preparedStatement.setString(1, selectedVehicle);
                preparedStatement.setString(2, fromDate);
                preparedStatement.setString(3, toDate);
                preparedStatement.setString(4, fromDate);
                preparedStatement.setString(5, toDate);
    
                ResultSet result = preparedStatement.executeQuery();
                return !result.next();
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


}
