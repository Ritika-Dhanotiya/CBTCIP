import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginPage extends JFrame {
    private final JTextField usernameField;
    private final JPasswordField passwordField;

    private final JButton loginButton, updateCredentialsButton;

    public LoginPage() {
        // Set up the frame
        setTitle("Login Page");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        // Username label and text field
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(10, 20, 80, 25);
        add(usernameLabel);

        usernameField = new JTextField(20);
        usernameField.setBounds(100, 20, 165, 25);
        add(usernameField);

        // Password label and text field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10, 50, 80, 25);
        add(passwordLabel);

        passwordField = new JPasswordField(20);
        passwordField.setBounds(100, 50, 165, 25);
        add(passwordField);

        // Login button
        loginButton = new JButton("Login");
        loginButton.setBounds(10, 80, 80, 25);
        add(loginButton);

        // Update credentials button
        updateCredentialsButton = new JButton("Update Credentials");
        updateCredentialsButton.setBounds(100, 80, 165, 25);
        add(updateCredentialsButton);

        // Login button action listener
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                if (authenticate(username, password)) {
                    JOptionPane.showMessageDialog(null, "Login successful!");
                    dispose(); // Close the login frame
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            new MCQExam().setVisible(true); // Open MCQ exam frame
                        }
                    });
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Update credentials button action listener
        updateCredentialsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                if (authenticate(username, password)) {
                    JOptionPane.showMessageDialog(null, "Authentication successful! You can now update your credentials.");
                    dispose(); // Close the login frame
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            new UpdateCredentialsFrame(username).setVisible(true); // Open update credentials frame
                        }
                    });
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        setVisible(true);
    }

    private boolean authenticate(String username, String password) {
        Connection connection = DatabaseConnection.getConnection();
        try {
            String sql = "SELECT * FROM Users WHERE username = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        new LoginPage();
    }
}

