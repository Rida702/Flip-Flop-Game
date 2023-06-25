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

public class Register extends JFrame {

    private JLabel label;
    private JTextField registerUsernameField;
    private JPasswordField registerPasswordField;
    private JTextField emailField;
    private JButton registorButton;
    private JButton loginButton;
    private JPanel contentPane;
    private Connection conn;
    private Statement stmt;

    public Register() {
        setTitle("Register");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(200, 100, 800, 500);
        setResizable(false);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Register");
        lblNewLabel.setForeground(Color.BLACK);
        lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 46));
        lblNewLabel.setBounds(350, 13, 273, 93);
        contentPane.add(lblNewLabel);

        registerUsernameField = new JTextField();
        registerUsernameField.setFont(new Font("Tahoma", Font.PLAIN, 32));
        registerUsernameField.setBounds(350, 150, 281, 50);
        contentPane.add(registerUsernameField);
        registerUsernameField.setColumns(10);

        registerPasswordField = new JPasswordField();
        registerPasswordField.setFont(new Font("Tahoma", Font.PLAIN, 32));
        registerPasswordField.setBounds(350, 230, 281, 50);
        contentPane.add(registerPasswordField);

        emailField = new JTextField();
        emailField.setFont(new Font("Tahoma", Font.PLAIN, 32));
        emailField.setBounds(350, 310, 281, 50);
        contentPane.add(emailField);

        JLabel lblUsername = new JLabel("Username");
        lblUsername.setBackground(Color.BLACK);
        lblUsername.setForeground(Color.BLACK);
        lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 28));
        lblUsername.setBounds(120, 140, 170, 52);
        contentPane.add(lblUsername);

        JLabel lblPassword = new JLabel("Password");
        lblPassword.setForeground(Color.BLACK);
        lblPassword.setBackground(Color.CYAN);
        lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 28));
        lblPassword.setBounds(120, 220, 170, 52);
        contentPane.add(lblPassword);

        JLabel lblEmail = new JLabel("Email");
        lblEmail.setForeground(Color.BLACK);
        lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 28));
        lblEmail.setBounds(120, 300, 170, 52);
        contentPane.add(lblEmail);

        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Tahoma", Font.PLAIN, 26));
        loginButton.setBounds(350, 392, 122, 53);

        registorButton = new JButton("Register");
        registorButton.setFont(new Font("Tahoma", Font.PLAIN, 26));
        registorButton.setBounds(472, 392, 122, 53);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(registorButton);
        buttonPanel.add(loginButton);

        contentPane.setLayout(new BorderLayout());

        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        registorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                register();
            }
        });
        
        loginButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Login loginpage = new Login();
            dispose(); // Close the login page
        }
    });


        try {
            conn = DriverManager.getConnection("jdbc:ucanaccess://webdb.accdb");
            stmt = conn.createStatement();
        } catch (SQLException se) {
            se.printStackTrace();
        }

        setVisible(true);
    }

    private void register() {
        String username = registerUsernameField.getText();
        String password = new String(registerPasswordField.getPassword());
        String email = emailField.getText();
        
        if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill in all the fields.");
            return; //
        }

        try {
            String sql = "INSERT INTO users (Uname, password, email) VALUES ('" + username + "', '" + password + "', '" + email +"')";
            int rowsAffected = stmt.executeUpdate(sql);

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Registration successful! You can now log in with your credentials.");
            } else {
                JOptionPane.showMessageDialog(this, "Registration failed. Please try again.");
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        Login loginpage1 = new Login();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Register();
            }
        });
    }
}

