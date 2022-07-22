package presentation;

import bll.DeliveryService;

import javax.swing.*;

public class AdministratorGUI extends JFrame {
    private JPanel administratorPanel;
    private JButton importBtn;
    private JTextField productNameTxt;
    private JTextField ratingTxt;
    private JButton removeBtn;
    private JTextField caloriesTxt;
    private JTextField proteinsTxt;
    private JTextField fatsTxt;
    private JTextField sodiumTxt;
    private JButton addBaseProductBtn;
    private JButton modifyProductBtn;
    private JTable compositeProductTable;
    private JButton addToCompositeProductBtn;
    private JTextField compositeProductTxt;
    private JButton addCompositeProductBtn;
    private JButton generateReportBtn;
    private JButton goBackBtn;
    private JTable menuTable;
    private JTextField priceTxt;
    private AdministratorController administratorController;
    private DeliveryService deliveryService;

    public AdministratorGUI(DeliveryService deliveryService) {
        super("Administrator Operations Window");
        setContentPane(administratorPanel);
        setSize(700, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        this.deliveryService = deliveryService;

        administratorController = new AdministratorController(this, deliveryService);
        administratorController.createImportButtonActionListener(importBtn);
        administratorController.createMenuTableMouseListener(menuTable);
        administratorController.createAddBaseProductButtonActionListener(addBaseProductBtn);
        administratorController.createRemoveProductButtonActionListener(removeBtn);
        administratorController.createModifyProductButtonActionListener(modifyProductBtn);
        administratorController.createAddToCompositeProductButtonActionListener(addToCompositeProductBtn);
        administratorController.createAddCompositeProductActionListener(addCompositeProductBtn);
        administratorController.createGenerateReportsButtonActionListener(generateReportBtn);
        administratorController.createGoBackBtnActionListener(goBackBtn);
    }

    public JTextField getCompositeProductTxt() {
        return compositeProductTxt;
    }

    public JTextField getProductNameTxt() {
        return productNameTxt;
    }

    public JTextField getRatingTxt() {
        return ratingTxt;
    }

    public JTextField getCaloriesTxt() {
        return caloriesTxt;
    }

    public JTextField getProteinsTxt() {
        return proteinsTxt;
    }

    public JTextField getFatsTxt() {
        return fatsTxt;
    }

    public JTextField getSodiumTxt() {
        return sodiumTxt;
    }

    public JTextField getPriceTxt() {
        return priceTxt;
    }

    public void setProductNameTxt(String str) {
        productNameTxt.setText(str);
    }

    public void setRatingTxt(String str) {
        ratingTxt.setText(str);
    }

    public void setCaloriesTxt(String str) {
        caloriesTxt.setText(str);
    }

    public void setProteinsTxt(String str) {
        proteinsTxt.setText(str);
    }

    public void setFatsTxt(String str) {
        fatsTxt.setText(str);
    }

    public void setSodiumTxt(String str) {
        sodiumTxt.setText(str);
    }

    public void setPriceTxt(String str) {
        priceTxt.setText(str);
    }

    public JTable getMenuTable() {
        return menuTable;
    }

    public JTable getCompositeProductTable() {
        return compositeProductTable;
    }
}
