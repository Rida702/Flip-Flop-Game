/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package flipflopgame;

/**
 *
 * @author abu musa traders
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.border.EmptyBorder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Login extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextField textField;
    private JPasswordField passwordField;
    private JButton btnNewButton;
    private JButton registorButton;
    private JLabel label;
    private JPanel contentPane;
    private Connection conn;
    private Statement stmt;

    public Login() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(200, 100, 800, 500);
        setResizable(false);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Login");
        lblNewLabel.setForeground(Color.BLACK);
        lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 46));
        lblNewLabel.setBounds(350, 13, 273, 93);
        contentPane.add(lblNewLabel);

        textField = new JTextField();
        textField.setFont(new Font("Tahoma", Font.PLAIN, 32));
        textField.setBounds(400, 170, 281, 68);
        contentPane.add(textField);
        textField.setColumns(10);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Tahoma", Font.PLAIN, 32));
        passwordField.setBounds(400, 286, 281, 68);
        contentPane.add(passwordField);

        JLabel lblUsername = new JLabel("Username");
        lblUsername.setBackground(Color.BLACK);
        lblUsername.setForeground(Color.BLACK);
        lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 31));
        lblUsername.setBounds(200, 166, 193, 52);
        contentPane.add(lblUsername);

        JLabel lblPassword = new JLabel("Password");
        lblPassword.setForeground(Color.BLACK);
        lblPassword.setBackground(Color.CYAN);
        lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 31));
        lblPassword.setBounds(200, 286, 193, 52);
        contentPane.add(lblPassword);

        btnNewButton = new JButton("Login");
        btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 26));
        btnNewButton.setBounds(350, 392, 122, 53);

        registorButton = new JButton("Register");
        registorButton.setFont(new Font("Tahoma", Font.PLAIN, 26));
        registorButton.setBounds(472, 392, 122, 53);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(btnNewButton);
        buttonPanel.add(registorButton);
        contentPane.setLayout(new BorderLayout());
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        btnNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
                dispose();
                FlipFlopGameGUI game = new FlipFlopGameGUI();
            }
        });
        
        registorButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Register registerPage = new Register();
            dispose(); // Close the login page
        }
});


        setVisible(true);
    }

    public void connectToDatabase() {
    try {
        conn = DriverManager.getConnection("jdbc:ucanaccess://webdb.accdb");
        stmt = conn.createStatement();
        System.out.println("Connected to the database.");
    } catch (SQLException se) {
        se.printStackTrace();
    }
}

    private void login() {
        connectToDatabase();
        String username = textField.getText();
        String password = new String(passwordField.getPassword());
        try {
            String sql = "SELECT * FROM users WHERE Uname = '" + username + "' AND password = '" + password + "'";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Login Successful! Welcome " + username);
                int id = rs.getInt("id");
                String uname = rs.getString("Uname");
                String email = rs.getString("email");

                System.out.println("ID: " + id);
                System.out.println("Name: " + uname);
                System.out.println("Email: " + email);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password. Please try again.");
            }

            rs.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Login();
            }
        });
    }
}
