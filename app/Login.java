/**
 * @author Botiz Alexandru-Gabriel
 */
package app;
 

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AuthUI());
    }
}

class AuthUI extends JFrame {
    private static final long serialVersionUID = 1L;
    
    public AuthUI() {
        setTitle("Welcome to Vacation Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(400, 200, 400, 200);
        setLayout(new GridLayout(2, 1));

        JButton loginButton = new JButton("Log In");
        JButton signUpButton = new JButton("Sign Up");

        loginButton.addActionListener(e -> new LoginUI(this));
        signUpButton.addActionListener(e -> new SignUpUI());

        add(loginButton);
        add(signUpButton);

        setVisible(true);
    }
    /**
     * Functie pentru a inchide fereastra
     */
    public void closeWindow() {
        this.dispose();
    }
}

class LoginUI extends JFrame {
	/**
	 * @param emailField este emailul contului
	 * @param passwordField este parola contului
	 * @param parentWindow este clasa parinte
	 */
    private static final long serialVersionUID = 1L;
    private JTextField emailField;
    private JPasswordField passwordField;
    private AuthUI parentWindow; 

    public LoginUI(AuthUI parentWindow) {
        this.parentWindow = parentWindow; 
        setTitle("Log In");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(450, 250, 350, 200);
        setLayout(new GridLayout(3, 2));

        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();

        JButton loginButton = new JButton("Log In");
        loginButton.addActionListener(new LoginHandler());
/**
 * Se adauga toate butoanele,labelurile,text field-urile
 */
        add(emailLabel);
        add(emailField);
        add(passwordLabel);
        add(passwordField);
        add(new JLabel());
        add(loginButton);

        setVisible(true);
    }

    private class LoginHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            /**
             * Functie care verifica daca text field-ul a fost completat sau nu
             */
            if (email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            /**
             * Conexiunea cu baza de date
             */
            String url = "jdbc:mysql://localhost:3306/jdbcdemo"; 
            String username = "root"; 
            String dbPassword = ""; 

            try (Connection connection = DriverManager.getConnection(url, username, dbPassword)) {
                String sql = "SELECT * FROM Users WHERE Email = ? AND Password = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setString(1, email);
                    preparedStatement.setString(2, password);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    if (resultSet.next()) {
                        JOptionPane.showMessageDialog(null, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        dispose();  
                        parentWindow.closeWindow(); 
                        openAppUI(); 
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid email or password!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }
    /**
     * Functie care deschide meniul main al aplicatiei
     */
    private void openAppUI() {
        AppUi app = new AppUi();
        app.setVisible(true);
    }
}