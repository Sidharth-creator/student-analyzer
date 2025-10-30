package ui;

import database.DataManager;

import javax.swing.*;
import java.awt.*;

public class AdminLoginDialog extends JDialog {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private DataManager dataManager;
    private StudentInfoDialog parentDialog;

    // Hardcoded credentials 
    private static final String ADMIN_USER = "hai";
    private static final String ADMIN_PASS = "123"; 

    public AdminLoginDialog(StudentInfoDialog parent, DataManager dataManager) {
        super(parent, "Admin Login", true);
        this.parentDialog = parent;
        this.dataManager = dataManager;
        setSize(300, 150);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formPanel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        formPanel.add(usernameField);

        formPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        formPanel.add(passwordField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton loginButton = new JButton("Login");
        JButton cancelButton = new JButton("Cancel");
        
        loginButton.addActionListener(e -> attemptLogin());
        cancelButton.addActionListener(e -> dispose());
        
        buttonPanel.add(loginButton);
        buttonPanel.add(cancelButton);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void attemptLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (ADMIN_USER.equals(username) && ADMIN_PASS.equals(password)) {
            dispose();
            // Open the Admin View
            new AdminViewFrame(parentDialog, dataManager).setVisible(true); 
        } else {
            JOptionPane.showMessageDialog(this, "Invalid Username or Password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }
}
