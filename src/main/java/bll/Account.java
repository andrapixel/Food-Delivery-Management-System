package bll;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Account implements Serializable {
    private int accountID;
    private String firstName;
    private String lastName;
    private String username;
    private char[] password;
    private UserType userType;
    private int placedOrders = 0;

    public Account() {}

    public Account(String username, char[] password, UserType userType) {
        this.username = username;
        this.password = password;
        this.userType = userType;
    }

    public Account(int accountID, String firstName, String lastName, String username, char[] password, UserType userType) {
        this.accountID = accountID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.userType = userType;
    }

    public int getPlacedOrders() {
        return placedOrders;
    }

    public void increment() {
        placedOrders++;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
