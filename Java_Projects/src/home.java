package src;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

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
import java.text.SimpleDateFormat;

public class home extends JFrame implements ActionListener{
    private final int homeWidth = 900;
    private final int homeHeight = 450;
    public static JFrame frame;
    public JLabel label;
    private JPanel mainPanel;
    private JMenu menu;
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
    private DefaultTableModel tableModel;
    private JTable table; 
    private Connection connection;

    home() throws SQLException {
        setSize(homeWidth, homeHeight);
        setTitle("ExoticRentals");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel(null);
        mainPanel.setBackground(Color.LIGHT_GRAY);

        menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        menu = new JMenu("Setting");
        menuBar.add(menu);

        JMenuItem logout = new JMenuItem("Log Out");
        logout.setActionCommand("Logout");
        logout.addActionListener(this);
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

        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        table.setModel(tableModel);

        String[] colNames = {"First Name", "Last Name", "Phone Number", "Credit Card", "CVV", "Expiration Date", "Vehicle Type", "From Date", "To Date"};
        tableModel.setColumnIdentifiers(colNames);

        JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(460, 20, 435, 365);
        mainPanel.add(scrollPane);

        int[] columnWidths = {100, 100, 100, 100, 50, 100, 100, 100, 100};
        setColumnWidths(table, columnWidths);

        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        refreshTable();

        setContentPane(mainPanel);
        setVisible(true);
    }

    private void setColumnWidths(JTable table, int[] widths) {
        TableColumnModel columnModel = table.getColumnModel();
        for (int i = 0; i < widths.length; i++) {
            TableColumn column = columnModel.getColumn(i);
            column.setPreferredWidth(widths[i]);
        }
    }

    public void refreshTable() throws SQLException {
        tableModel.setRowCount(0);
        String[] colNames = {"First Name", "Last Name", "Phone Number", "Credit Card", "CVV", "Expiration Date", "Vehicle Type", "From Date", "To Date"};
    
        if (tableModel.getColumnCount() == 0) {
            tableModel.setColumnIdentifiers(colNames);
        }

        Conn c = new Conn();
        ResultSet resultSet = c.getDataFromDatabase();

        try {
            if (resultSet != null) {
                while (resultSet.next()) {
                    String[] rowData = {
                            resultSet.getString("f_name"),
                            resultSet.getString("l_name"),
                            resultSet.getString("phone_number"),
                            resultSet.getString("credit_card"),
                            resultSet.getString("cvv"),
                            resultSet.getString("expirationDate"),
                            resultSet.getString("vehicle_type"),
                            resultSet.getString("From_date"),
                            resultSet.getString("To_date")
                    };
                    tableModel.addRow(rowData);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == continueButton) {
            String firstNameInput1 = first_nameInput.getText();
            String lastNameInput1 = last_nameInput.getText();
            String selectedVehicle = (String) carOptions.getSelectedItem();
            String cardInfoInput1 = cardInfoInput.getText();
            String cvvInput1 = cvvInput.getText();

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String fromDate1 = (fromDate.getDate() != null) ? dateFormat.format(fromDate.getDate()) : null;
            String toDate1 = (toDate.getDate() != null) ? dateFormat.format(toDate.getDate()) : null;
            String expDate = (expirationDate.getDate() != null) ? dateFormat.format(expirationDate.getDate()) : null;

            String phoneInput1 = phoneInput.getText();

            boolean hasEmptyField = false;

            if (firstNameInput1.equals("") || selectedVehicle.equals("") || cardInfoInput1.equals("") || cvvInput1.equals("")
                    || fromDate1 == null || toDate1 == null || expDate == null || phoneInput1.equals("")) {
                hasEmptyField = true;
                JOptionPane.showMessageDialog(this, "One or More Fields Are Empty", "Error", JOptionPane.ERROR_MESSAGE);
            }

            if (!hasEmptyField) {
                Conn c = new Conn();
                c.insertCustomerData(firstNameInput1, lastNameInput1, phoneInput1, cardInfoInput1, cvvInput1, expDate,
                        selectedVehicle, fromDate1, toDate1);
                first_nameInput.setText("");
                last_nameInput.setText("");
                cardInfoInput.setText("");
                cvvInput.setText("");
                fromDate.setDate(null);
                toDate.setDate(null);
                expirationDate.setDate(null);
                phoneInput.setText("");
                try {
                    refreshTable();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        }
        else if (e.getActionCommand().equals("Logout")) { 
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to log out?", "Confirm Logout", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
                login l = new login();
            }
        }
} 
}