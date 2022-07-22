package presentation;

import bll.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class ClientController {
    private ClientGUI clientGUI;
    private DeliveryService deliveryService;
    private List<MenuItem> searchedItemsList = new ArrayList<>();
    private List<MenuItem> orderedItems;
    private static int searched = 0;
    private String searchFilterLbl = "[Search filter] ";

    private enum TableType {
        PRODUCTS,
        ORDER,
        SEARCHED
    }

    public ClientController(ClientGUI clientGUI, DeliveryService deliveryService) {
        this.clientGUI = clientGUI;
        this.orderedItems = new ArrayList<>();
        this.deliveryService = deliveryService;
    }

    public void createGoBackBtnActionListener(JButton goBackBtn) {
        goBackBtn.addActionListener(e -> {
            new LoginGUI(deliveryService);
            clientGUI.dispose();
        });
    }

    public void createViewProductsButtonActionListener(JButton viewBtn) {
        viewBtn.addActionListener(e -> {
            configureJTable(clientGUI.getProductsTable(), new DefaultTableModel(), TableType.PRODUCTS);
            searchedItemsList = deliveryService.getMenu();
            clientGUI.getSearchLbl().setText("");
            searchFilterLbl = "[Search filter] ";
        });
    }

    private void configureJTable(JTable table, DefaultTableModel tableModel, TableType tableType) {
        String[] columnNames = {"Title", "Rating", "Calories", "Proteins", "Fats", "Sodium", "Price"};

        for (String columnName : columnNames) {
            tableModel.addColumn(columnName);
        }

        if (tableType == TableType.PRODUCTS)
            importMenuItemsToTable(tableModel, 0);
        else if (tableType == TableType.SEARCHED)
            importMenuItemsToTable(tableModel, 1);
        else
            populateOrderTable(tableModel);

        table.setModel(tableModel);
    }

    private void importMenuItemsToTable(DefaultTableModel tableModel, int searched) {
        if (searched == 0) {
            for (MenuItem mi : deliveryService.getMenu()) {
                tableModel.addRow(new Object[]{mi.getTitle(), String.valueOf(mi.getRating()), String.valueOf(mi.getCalories()),
                        String.valueOf(mi.getProteins()), String.valueOf(mi.getFats()), String.valueOf(mi.getSodium()), String.valueOf(mi.getPrice())});
            }
        }
        else {
            for (MenuItem mi : searchedItemsList) {
                tableModel.addRow(new Object[]{mi.getTitle(), String.valueOf(mi.getRating()), String.valueOf(mi.getCalories()),
                        String.valueOf(mi.getProteins()), String.valueOf(mi.getFats()), String.valueOf(mi.getSodium()), String.valueOf(mi.getPrice())});
            }
        }
    }

    private void populateOrderTable(DefaultTableModel tableModel) {
        for (MenuItem mi : orderedItems) {
            tableModel.addRow(new Object[]{mi.getTitle(), String.valueOf(mi.getRating()), String.valueOf(mi.getCalories()),
                    String.valueOf(mi.getProteins()), String.valueOf(mi.getFats()), String.valueOf(mi.getSodium()), String.valueOf(mi.getPrice())});
        }
    }

    public void createSearchButtonActionListener(JButton searchBtn) {
        searchBtn.addActionListener(e -> {
            if (searched == 0) {

                searchedItemsList = deliveryService.searchProduct(SearchCriteria.valueOf(clientGUI.getSearchFilterCBSelection().toUpperCase()),
                        clientGUI.getSearchTxt().getText(), deliveryService.getMenu());
                configureJTable(clientGUI.getProductsTable(), new DefaultTableModel(), TableType.SEARCHED);
                searched++;
                searchFilterLbl += clientGUI.getSearchFilterCBSelection().toUpperCase() + ": " + clientGUI.getSearchTxt().getText() + "; ";
                clientGUI.getSearchLbl().setText(searchFilterLbl);
            }
            else if (searched > 0) {
                searchedItemsList = deliveryService.searchProduct(SearchCriteria.valueOf(clientGUI.getSearchFilterCBSelection().toUpperCase()),
                        clientGUI.getSearchTxt().getText(), searchedItemsList);
                configureJTable(clientGUI.getProductsTable(), new DefaultTableModel(), TableType.SEARCHED);
                searchFilterLbl += clientGUI.getSearchFilterCBSelection().toUpperCase() + ": " + clientGUI.getSearchTxt().getText() + "; ";
                clientGUI.getSearchLbl().setText(searchFilterLbl);
            }
        });
    }

    public void createOrderCreationButtonActionListener(JButton orderBtn) {
        orderBtn.addActionListener(e -> {
            if (!orderedItems.isEmpty()) {
                List<MenuItem> finalOrderItems = new ArrayList<>();
                finalOrderItems.addAll(orderedItems);
                deliveryService.createOrder(clientGUI.getClient(), finalOrderItems);
                for(MenuItem mi : finalOrderItems)
                    System.out.println(mi.getTitle());
            }
            else
                System.out.println("No items were added to the order.");    // TODO: pop-up error messages
        });
    }

    public void createAddToOrderBtnActionListener(JButton addOrderBtn) {
        addOrderBtn.addActionListener(e -> {
            if (getSelectedItemFromTable() != null) {
                orderedItems.add(getSelectedItemFromTable());
                System.out.println(getSelectedItemFromTable().getTitle() + "has been added to the order.");
                configureJTable(clientGUI.getOrderedProductsTable(), new DefaultTableModel(), TableType.ORDER);
            }
            else System.out.println("No item was selected."); // TODO: pop-up error messages :(
        });
    }

    private BaseProduct getSelectedItemFromTable() {
        int selectedRow = clientGUI.getProductsTable().getSelectedRow();
        if (selectedRow != -1)
            return new BaseProduct((String)clientGUI.getProductsTable().getValueAt(selectedRow, 0),
                    Float.parseFloat((String)clientGUI.getProductsTable().getValueAt(selectedRow, 1)),
                    Integer.parseInt((String)clientGUI.getProductsTable().getValueAt(selectedRow, 2)),
                    Integer.parseInt((String)clientGUI.getProductsTable().getValueAt(selectedRow, 3)),
                    Integer.parseInt((String)clientGUI.getProductsTable().getValueAt(selectedRow, 4)),
                    Integer.parseInt((String)clientGUI.getProductsTable().getValueAt(selectedRow, 5)),
                    Float.parseFloat((String)clientGUI.getProductsTable().getValueAt(selectedRow, 6))
                    );

        return null;
    }
}
