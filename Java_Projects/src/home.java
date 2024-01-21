package src;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;

import java.util.*;
import java.util.concurrent.Flow;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class home extends JFrame implements ActionListener{
    private final int homeWidth = 900;
    private final int homeHeight = 450;
    public static JFrame frame;
    public JLabel label;
    private JPanel mainPanel;
    private JMenuBar menuBar;
    private JCalendar calendar;
    private String[] carOptionsList;
    private JTextField first_nameInput;
    private JTextField last_nameInput;
    private JTextField cardInfoInput;
    private JDateChooser fromDate;
    private JDateChooser toDate;
    private JComboBox carOptions;
    private JTextField cvvInput;
    private JTextField phoneInput;
    private JDateChooser expirationDate;
    private JButton continueButton;
    private AbstractTableModel tableModel;
    private JTable table; 

    home() {
        setSize(homeWidth, homeHeight);
        setTitle("ExoticRentals");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel(null);
        mainPanel.setBackground(Color.LIGHT_GRAY);

        menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu menu = new JMenu("Setting");
        menuBar.add(menu);

        JMenuItem vehicles = new JMenu("Vehicles");
        menuBar.add(vehicles);

        JMenuItem vehicles_ins = new JMenuItem("Vehicles Insurance");
        vehicles.add(vehicles_ins);

        JMenuItem vehicles_his = new JMenuItem("Vehicles History");
        vehicles.add(vehicles_his);

        JMenuItem logout = new JMenuItem("Log Out");
        menu.add(logout);

        JPanel squareBoxPanel = new JPanel(null);
        squareBoxPanel.setBounds(20, 20, 435, 360);
        squareBoxPanel.setBackground(new Color(200,200,200)); 
        mainPanel.add(squareBoxPanel);

        JLabel first_name = new JLabel("First Name: ");
        first_name.setFont(new Font("Arial", Font.BOLD, 16));
        first_name.setBounds(20,20, 100, 20);
        squareBoxPanel.add(first_name);

        first_nameInput = new JTextField();
        first_nameInput.setBounds(120, 20, 300, 20);
        squareBoxPanel.add(first_nameInput);

        JLabel last_name = new JLabel("Last Name: ");
        last_name.setFont(new Font("Arial", Font.BOLD, 16));
        last_name.setBounds(20,60, 100, 20);
        squareBoxPanel.add(last_name);

        last_nameInput = new JTextField();
        last_nameInput.setBounds(120, 60, 300, 20);
        squareBoxPanel.add(last_nameInput);

        JLabel vehicle = new JLabel("Vehicle: ");
        vehicle.setFont(new Font("Arial", Font.BOLD, 16));
        vehicle.setBounds(20,100, 100, 20);
        squareBoxPanel.add(vehicle);

        carOptionsList = new String[]{"Tesla Model X", "Toyota Camry", "Kia K5", "Mercedes-Benz AMG GT63"};
        carOptions = new JComboBox<>(carOptionsList);
        carOptions.setBounds(85, 82, 150, 60);
        squareBoxPanel.add(carOptions);

        fromDate = new JDateChooser();
        fromDate.setBounds(75, 265, 150, 20);  
        squareBoxPanel.add(fromDate);

        toDate = new JDateChooser();
        toDate.setBounds(75, 300, 150, 20); 
        squareBoxPanel.add(toDate);

        JLabel cardInfo = new JLabel("Credit Card: ");
        cardInfo.setFont(new Font("Arial", Font.BOLD, 16));
        cardInfo.setBounds(20, 140, 200, 20);
        squareBoxPanel.add(cardInfo);

        cardInfoInput = new JTextField();
        cardInfoInput.setBounds(120, 140, 260, 20);
        squareBoxPanel.add(cardInfoInput);

        JLabel expirationText = new JLabel("Expiration Date:");
        expirationText.setFont(new Font("Arial", Font.BOLD, 16));
        expirationText.setBounds(20, 180, 150, 20);
        squareBoxPanel.add(expirationText);

        expirationDate = new JDateChooser();
        expirationDate.setBounds(160, 180, 100, 20);
        squareBoxPanel.add(expirationDate);

        JLabel cvvText = new JLabel("CVV: ");
        cvvText.setFont(new Font("Arial", Font.BOLD, 16));
        cvvText.setBounds(280, 180, 50, 20);  
        squareBoxPanel.add(cvvText);

        cvvInput = new JTextField();
        cvvInput.setBounds(330, 180, 80, 20); 
        squareBoxPanel.add(cvvInput);

        JLabel phoneLabel = new JLabel("Phone Number: ");
        phoneLabel.setFont(new Font("Arial", Font.BOLD, 16));
        phoneLabel.setBounds(20, 220, 150, 20);
        squareBoxPanel.add(phoneLabel);

        phoneInput = new JTextField();
        phoneInput.setBounds(160, 220, 200, 20);
        squareBoxPanel.add(phoneInput);
        
        JLabel fromDateText = new JLabel("From: ");
        fromDateText.setFont(new Font("Arial", Font.BOLD, 16));
        fromDateText.setBounds(20, 265, 150, 20);
        squareBoxPanel.add(fromDateText);

        JLabel toDateText = new JLabel("To: ");
        toDateText.setFont(new Font("Arial", Font.BOLD, 16));
        toDateText.setBounds(20, 300, 150, 20);
        squareBoxPanel.add(toDateText);

        continueButton = new JButton("Continue");
        continueButton.setFont(new Font("Arial", Font.BOLD, 16));
        continueButton.setBackground(Color.BLUE);
        continueButton.setBounds(300, 310, 125, 40);
        continueButton.addActionListener(this);
        squareBoxPanel.add(continueButton);

        DefaultTableModel tableModel = new DefaultTableModel();
        JTable table = new JTable();
        table.setBounds(450, 20, 425, 365);
        mainPanel.add(table);


        setContentPane(mainPanel);
        setVisible(true);
    }

    // private void refreshTable() {
    //     DefaultTableModel tableModel = new DefaultTableModel();
    //     tableModel.setRowCount(0);
    //     Conn c = new Conn();
    //     ResultSet resultSet = c.getDataFromDatabase();

    //     try {
    //         while (resultSet.next()) {
    //             // Add a row to the table model
    //             Object[] rowData = {
    //                     resultSet.getString("first_name"),
    //                     resultSet.getString("last_name"),
    //                     resultSet.getString("phone"),
    //                     resultSet.getString("card_info"),
    //                     resultSet.getString("cvv"),
    //                     resultSet.getString("exp_date"),
    //                     resultSet.getString("vehicle"),
    //                     resultSet.getString("from_date"),
    //                     resultSet.getString("to_date")
    //             };
    //             tableModel.addRow(rowData);
    //         }
    //     } catch (SQLException ex) {
    //         ex.printStackTrace();
    //     }
    // }

    public void actionPerformed(ActionEvent e) {
        String firstNameInput1 = first_nameInput.getText();
        String lastNameInput1 = last_nameInput.getText();
        String selectedVehicle = (String) carOptions.getSelectedItem();
        String cardInfoInput1 = cardInfoInput.getText();
        String cvvInput1 = cvvInput.getText();
        String fromDate1 = fromDate.getDate().toString();
        String toDate1 = toDate.getDate().toString();
        String expDate = expirationDate.getDate().toString();
        String phoneInput1 = phoneInput.getText();  
    
        boolean hasEmptyField = false;
    
        if (firstNameInput1.equals("") || selectedVehicle.equals("") || cardInfoInput1.equals("") || cvvInput1.equals("")
                || fromDate1.equals("") || toDate1.equals("") || expDate.equals("") || phoneInput1.equals("")) {
            hasEmptyField = true;
            JOptionPane.showMessageDialog(this, "One or More Fields Are Empty", "Error", JOptionPane.ERROR_MESSAGE);
        }
    
        if (!hasEmptyField) {
            Conn c = new Conn();
            c.insertCustomerData(firstNameInput1, lastNameInput1, phoneInput1, cardInfoInput1, cvvInput1, expDate,
                    selectedVehicle, fromDate1, toDate1); 
            dispose();
            home h = new home();
    }
}
} 
