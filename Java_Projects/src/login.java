package src;
import javax.swing.*;
import javax.swing.table.*;
import java.util.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;


public class login extends JFrame implements ActionListener{
    private final int homeWidth = 1200;
    private final int homeHeight = 400;
    public static JFrame frame;
    public ImageIcon image;
    public JLabel label;
    public JPanel mainPanel;
    public JTextField userNameType;
    public JPasswordField passwordType;
    public Statement s;
    public Connection c;

    login() {
        setSize(homeWidth, homeHeight);
        setTitle("ExoticRentals");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel(null); 
        mainPanel.setBackground(Color.LIGHT_GRAY);

        image = new ImageIcon(getClass().getResource("kiak5.png"));
        Image image1 = image.getImage().getScaledInstance(600, 400, Image.SCALE_SMOOTH);
        image = new ImageIcon(image1);
        label = new JLabel(image);
        label.setBounds(0, 0, 600, 400);
        mainPanel.add(label);

        JLabel welcome = new JLabel("Welcome to ExoticRentals");
        welcome.setFont(new Font("Arial", Font.BOLD, 24));
        welcome.setBounds(750, 50, 300, 30);
        mainPanel.add(welcome);

        JLabel userName = new JLabel("Username: ");
        userName.setFont(new Font("Arial", Font.BOLD, 16));
        userName.setBounds(675,150, 100, 18);
        mainPanel.add(userName);

        userNameType = new JTextField();
        userNameType.setBounds(760, 145, 300, 30);
        mainPanel.add(userNameType);

        JLabel password = new JLabel("Password: ");
        password.setFont(new Font("Arial", Font.BOLD,  16));
        password.setBounds(675,220, 100, 18);
        mainPanel.add(password);

        passwordType = new JPasswordField();
        passwordType.setBounds(760, 215, 300, 30);
        mainPanel.add(passwordType);

        JButton login = new JButton("Sign In");
        login.setBounds(925,300, 100, 35);
        login.setBackground(Color.DARK_GRAY);
        login.addActionListener(this);
        mainPanel.add(login);

        JButton signUp = new JButton("Sign Up");
        signUp.setBounds(800,300, 100, 35);
        signUp.setBackground(Color.DARK_GRAY);
        signUp.addActionListener(this);
        mainPanel.add(signUp);

        setContentPane(mainPanel);
        setVisible(true);
    }

    public void validateAccount (String username, String password) {
        try {
            Conn conn = new Conn();
            boolean employeeExists = conn.employeeExists(username);

            if (employeeExists) {
                boolean passwordCorrect = conn.passwordExists(username, password);
                if (passwordCorrect) {
                    dispose();
                    home home = new home();
                } else {
                    JOptionPane.showMessageDialog(this, "Incorrect Password", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Employee ID", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Sign In")) {
            validateAccount(userNameType.getText(), new String(passwordType.getPassword()));
        }
        if (e.getActionCommand().equals("Sign Up")) {
            dispose();
            signup signup = new signup();
        }
    }

    public static void main(String[] args) {
        login h = new login();
        Conn con = new Conn();
        // SwingUtilities.invokeLater(() -> new home());
    }
}
