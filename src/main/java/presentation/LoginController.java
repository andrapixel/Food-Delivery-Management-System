package presentation;

import bll.Account;
import bll.DeliveryService;
import bll.UserType;
import dao.Serializator;

import javax.swing.*;
import java.util.*;

// TODO: propmpt the user to never leave empty fields at SignUp -> error message
public class LoginController {
    private LoginGUI loginGUI;
    private DeliveryService deliveryService;

    public LoginController(LoginGUI loginGUI, DeliveryService deliveryService) {
        this.loginGUI = loginGUI;
        this.deliveryService = deliveryService;
    }

    public void createLogInButtonActionListener(JButton loginBtn) {
        loginBtn.addActionListener(e -> {
            for (Account acc : deliveryService.getRegisteredAccounts()) {
                if (loginGUI.getUsernameLogin().equals(acc.getUsername())) {
                    if (Arrays.equals(loginGUI.getPasswordLogin(), acc.getPassword())) {
                        switch (acc.getUserType()) {
                            case ADMINISTRATOR:
                                new AdministratorGUI(deliveryService);
                                loginGUI.dispose();
                                break;
                            case CLIENT:
                                new ClientGUI(acc, deliveryService);
                                loginGUI.dispose();
                                break;
                            case EMPLOYEE:
                                new EmployeeGUI(deliveryService);
                                loginGUI.dispose();
                                break;
                        }
                    }
                }
            }
        });
    }

    public void createSignUpButtonActionListener(JButton signupBtn) {
        signupBtn.addActionListener(e -> {
            Account newAcc = new Account();
            if (Arrays.equals(loginGUI.getPasswordSignup(), loginGUI.getPasswordConfirmation())) {  // if the password and password confirmation coincide, a new account is created
                newAcc.setFirstName(loginGUI.getFirstName());
                newAcc.setLastName(loginGUI.getLastName());
                newAcc.setUsername(loginGUI.getUsernameSignup());
                newAcc.setPassword(loginGUI.getPasswordSignup());
                newAcc.setUserType(UserType.valueOf(loginGUI.getUserTypeAtSignup().toUpperCase()));
                if (newAcc.getUserType() == UserType.CLIENT)
                    newAcc.setAccountID(deliveryService.getRegisteredAccounts().size() + 1);
                else
                    newAcc.setAccountID(-1);

                Serializator<DeliveryService> serializator = new Serializator<>();
                deliveryService.getRegisteredAccounts().add(newAcc);

                serializator.serializeObject(deliveryService, "serialization.txt");
                System.out.println("A new account has been created successfully.");
                loginGUI.getTabbedPane().setSelectedIndex(0);
                deliveryService.displayAccounts();
            }
            else {
                JOptionPane.showMessageDialog(null, "The entered passwords do not match! Try again.",
                        "Password Mismatch Error", JOptionPane.ERROR_MESSAGE);
                loginGUI.setFirstName("");
                loginGUI.setLastName("");
                loginGUI.setUsernameSignup("");
                loginGUI.setPasswordSignup("");
                loginGUI.setPasswordConfirmation("");
                loginGUI.resetUserTypeAtSignup();
            }
        });
    }

    public void createExitButtonActionListener(JButton exitBtn) {
        exitBtn.addActionListener(e -> System.exit(0));
    }
}