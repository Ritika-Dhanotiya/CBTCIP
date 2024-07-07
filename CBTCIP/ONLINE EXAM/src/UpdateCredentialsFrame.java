import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateCredentialsFrame extends JFrame {
    private JTextField newUsernameField;
    private JPasswordField newPasswordField;
    private JButton updateButton;
    private String currentUsername;

    public UpdateCredentialsFrame(String username) {
        this.currentUsername = username;

        // Set up the frame
        setTitle("Update Credentials");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        // New username label and text field
        JLabel newUsernameLabel = new JLabel("New Username:");
        newUsernameLabel.setBounds(10, 20, 100, 25);
        add(newUsernameLabel);

        newUsernameField = new JTextField(20);
        newUsernameField.setBounds(120, 20, 150, 25);
        add(newUsernameField);

        // New password label and text field
        JLabel newPasswordLabel = new JLabel("New Password:");
        newPasswordLabel.setBounds(10, 50, 100, 25);
        add(newPasswordLabel);

        newPasswordField = new JPasswordField(20);
        newPasswordField.setBounds(120, 50, 150, 25);
        add(newPasswordField);

        // Update button
        updateButton = new JButton("Update");
        updateButton.setBounds(120, 80, 100, 25);
        add(updateButton);

        // Update button action listener
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newUsername = newUsernameField.getText();
                String newPassword = new String(newPasswordField.getPassword());
                if (updateCredentials(currentUsername, newUsername, newPassword)) {
                    JOptionPane.showMessageDialog(null, "Credentials updated successfully!");
                    dispose(); // Close the update frame
                    new LoginPage(); // Open login page again
                } else {
                    JOptionPane.showMessageDialog(null, "Error updating credentials", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        setVisible(true);
    }

    private boolean updateCredentials(String currentUsername, String newUsername, String newPassword) {
        Connection connection = DatabaseConnection.getConnection();
        try {
            String sql = "UPDATE Users SET username = ?, password = ? WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, newUsername);
            preparedStatement.setString(2, newPassword);
            preparedStatement.setString(3, currentUsername);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}


