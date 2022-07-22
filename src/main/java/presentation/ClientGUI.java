package presentation;

import bll.Account;
import bll.DeliveryService;

import javax.swing.*;

public class ClientGUI extends JFrame {
    private JPanel clientPanel;
    private JButton searchBtn;
    private JComboBox searchFilterCB;
    private JTextField searchTxt;
    private JTable productsTable;
    private JButton viewProductsBtn;
    private JButton createOrderBtn;
    private JTable orderedProductsTable;
    private JButton addProductToOrderBtn;
    private JButton goBackBtn;
    private JLabel welcomeLbl;
    private JLabel searchLbl;
    private ClientController clientController;
    private DeliveryService deliveryService;
    private Account client;

    public ClientGUI(Account client, DeliveryService deliveryService) {
        super("Client Operations Window");
        setContentPane(clientPanel);
        setSize(750, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        this.client = client;
        this.deliveryService = deliveryService;
        welcomeLbl.setText("Welcome, " + client.getFirstName() + "!");

        clientController = new ClientController(this, deliveryService);
        clientController.createViewProductsButtonActionListener(viewProductsBtn);
        clientController.createSearchButtonActionListener(searchBtn);
        clientController.createAddToOrderBtnActionListener(addProductToOrderBtn);
        clientController.createOrderCreationButtonActionListener(createOrderBtn);
        clientController.createGoBackBtnActionListener(goBackBtn);
    }

    public Account getClient() {
        return this.client;
    }

    public String getSearchFilterCBSelection() {
        return searchFilterCB.getSelectedItem().toString();
    }

    public JTextField getSearchTxt() {
        return searchTxt;
    }

    public JTable getProductsTable() {
        return productsTable;
    }

    public JTable getOrderedProductsTable() {
        return orderedProductsTable;
    }

    public JLabel getSearchLbl() {
        return searchLbl;
    }
}
