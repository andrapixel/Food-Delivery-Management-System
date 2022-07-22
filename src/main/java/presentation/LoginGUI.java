package presentation;

import bll.DeliveryService;

import javax.swing.*;

public class LoginGUI extends JFrame {
    private JPanel mainPanel;
    private JTabbedPane tabbedPane;
    private JPanel loginPanel;
    private JPanel signupPanel;
    private JTextField usernameLiTxt;
    private JPasswordField passwordLiTxt;
    private JComboBox userTypeLiCB;
    private JButton loginBtn;
    private JButton exitLiBtn;
    private JTextField firstNameTxt;
    private JTextField lastNameTxt;
    private JTextField usernameSuTxt;
    private JTextField emailTxt;
    private JPasswordField passwordSuTxt;
    private JPasswordField passwordSuTxt2;
    private JButton signupBtn;
    private JButton exitSuBtn;
    private JComboBox userTypeSuCB;
    private LoginController loginController;
    private DeliveryService deliveryService;

    public LoginGUI(DeliveryService deliveryService) {
        super("Register Window");
        setContentPane(mainPanel);
        setSize(500, 360);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        this.deliveryService = deliveryService;

        this.tabbedPane.setSelectedIndex(1);
        loginController = new LoginController(this, deliveryService);
        // Login Connections
        loginController.createLogInButtonActionListener(loginBtn);
        loginController.createExitButtonActionListener(exitLiBtn);

        // Signup Connections
        loginController.createSignUpButtonActionListener(signupBtn);
        loginController.createExitButtonActionListener(exitSuBtn);
    }

    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }

    // Log In components
    public String getUsernameLogin() {
        return usernameLiTxt.getText();
    }

    public void setUsernameLogin(String usernameStr) {
        usernameLiTxt.setText(usernameStr);
    }

    public char[] getPasswordLogin() {
        return passwordLiTxt.getPassword();
    }

    public void setPasswordLogin(String passwordStr) {
        passwordLiTxt.setText("");
    }

    public String getUserTypeAtLogin() {
        return userTypeLiCB.getSelectedItem().toString();
    }

    public void setUserTypeAtLogin() {
        userTypeLiCB.setSelectedItem(userTypeLiCB.getItemAt(0));
    }

    // Sign Up components
    public String getFirstName() {
        return firstNameTxt.getText();
    }

    public void setFirstName(String firstNameStr) {
        firstNameTxt.setText(firstNameStr);
    }

    public String getLastName() {
        return lastNameTxt.getText();
    }

    public void setLastName(String lastNameStr) {
        lastNameTxt.setText(lastNameStr);
    }

    public String getUsernameSignup() {
        return usernameSuTxt.getText();
    }

    public void setUsernameSignup(String usernameStr) {
        usernameSuTxt.setText(usernameStr);
    }

    public char[] getPasswordSignup() {
        return passwordSuTxt.getPassword();
    }

    public void setPasswordSignup(String passwordStr) {
        passwordSuTxt.setText(passwordStr);
    }

    public char[] getPasswordConfirmation() {
        return passwordSuTxt2.getPassword();
    }

    public void setPasswordConfirmation(String passwordStr) {
        passwordSuTxt2.setText(passwordStr);
    }

    public String getUserTypeAtSignup() {
        return userTypeSuCB.getSelectedItem().toString();
    }

    public void resetUserTypeAtSignup() {
        userTypeSuCB.setSelectedItem(userTypeSuCB.getItemAt(0));
    }
}