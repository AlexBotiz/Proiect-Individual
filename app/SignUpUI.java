/**
 * @author Botiz Alexandru-Gabriel
 */
package app;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * @variable emailField Casuta cu text pentru email
 * @variable passwordField Casuta cu text pentru parola
 */
class SignUpUI extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextField emailField;
    private JPasswordField passwordField;

    public SignUpUI() {
        setTitle("Sign Up");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(450, 250, 350, 200);
        setLayout(new GridLayout(3, 2));

        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();

        JButton signUpButton = new JButton("Sign Up");
        signUpButton.addActionListener(new SignUpHandler());

        add(emailLabel);
        add(emailField);
        add(passwordLabel);
        add(passwordField);
        add(new JLabel());
        add(signUpButton);

        setVisible(true);
    }

    private class SignUpHandler implements ActionListener {
        @Override
        /**
         * Functie care verifica daca casutele cu text din sign up sunt goale si in acelasi timp adauga contul in baza de date.
         */
        public void actionPerformed(ActionEvent e) {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            if (email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String url = "jdbc:mysql://localhost:3306/jdbcdemo"; 
            String username = "root"; 
            String dbPassword = ""; 

            try (Connection connection = DriverManager.getConnection(url, username, dbPassword)) {
                String sql = "INSERT INTO Users (Email, Password) VALUES (?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setString(1, email);
                    preparedStatement.setString(2, password);
                    preparedStatement.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Account created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }
}
