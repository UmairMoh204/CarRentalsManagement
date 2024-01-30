package src;
import javax.swing.*;
import java.util.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class signup extends JFrame implements ActionListener{
    private final int homeWidth = 700;
    private final int homeHeight = 450;
    public static JFrame frame;
    public JLabel label;
    public JPanel mainPanel;
    public JTextField firstNameInput;
    public JTextField lastNameInput;
    public JTextField employeeIDInput;
    public JTextField passwordInput;

    signup() {
        setSize(homeWidth, homeHeight);
        setTitle("ExoticRentals");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel1 = new JPanel(null);
        mainPanel1.setBackground(Color.LIGHT_GRAY);

        JLabel signupTitle = new JLabel("Sign Up");
        signupTitle.setFont(new Font("Arial", Font.BOLD, 24));
        signupTitle.setBounds(310, 25, 300, 50);
        mainPanel1.add(signupTitle);

        JLabel firstName = new JLabel("First Name: ");
        firstName.setFont(new Font("Arial", Font.BOLD, 16));
        firstName.setBounds(100, 100, 300, 50);
        mainPanel1.add(firstName);

        firstNameInput = new JTextField();
        firstNameInput.setBounds(200, 110, 350, 35);
        mainPanel1.add(firstNameInput);

        JLabel lastName = new JLabel("Last Name: ");
        lastName.setFont(new Font("Arial", Font.BOLD, 16));
        lastName.setBounds(100, 150, 300, 50);
        mainPanel1.add(lastName);

        lastNameInput = new JTextField();
        lastNameInput.setBounds(200, 159, 350, 35);
        mainPanel1.add(lastNameInput);

        JLabel employeeID = new JLabel("Employee ID: ");
        employeeID.setFont(new Font("Arial", Font.BOLD, 16));
        employeeID.setBounds(100, 200, 300, 50);
        mainPanel1.add(employeeID);

        employeeIDInput = new JTextField();
        employeeIDInput.setBounds(210, 210, 340, 35);
        mainPanel1.add(employeeIDInput);

        JLabel password = new JLabel("Password: ");
        password.setFont(new Font("Arial", Font.BOLD, 16));
        password.setBounds(100, 250, 300, 50);
        mainPanel1.add(password);

        passwordInput = new JTextField();
        passwordInput.setBounds(190, 260, 360, 35);
        mainPanel1.add(passwordInput);

        JButton signUp = new JButton("Sign Up");
        signUp.setBounds(310, 350, 100, 35);
        signUp.setBackground(Color.DARK_GRAY);
        signUp.addActionListener(this);
        mainPanel1.add(signUp);

        setContentPane(mainPanel1);
        setVisible(true);
    }

    private Properties loadConfig() throws IOException {
        Properties config = new Properties();
        try (InputStream input = new FileInputStream("config.properties")) {
            config.load(input);
        }
        return config;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Sign Up")) {
            String firstName = firstNameInput.getText();
            String lastName = lastNameInput.getText();
            String employeeID = employeeIDInput.getText();
            String password = passwordInput.getText();
            
            boolean hasEmptyField = false;

            if (firstName.equals("") || lastName.equals("") || employeeID.equals("") || password.equals("") ) {
                hasEmptyField = true;
                JOptionPane.showMessageDialog(this, "One or More Fields Are Empty", "Error", JOptionPane.ERROR_MESSAGE);
            }
            if (!hasEmptyField) {
                Conn c = new Conn();
                c.insertData(firstName, lastName, employeeID, password);
                dispose();
                try {
                    home h = new home();
                }
                catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
