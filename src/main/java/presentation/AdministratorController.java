package presentation;

import bll.BaseProduct;
import bll.CompositeProduct;
import bll.DeliveryService;
import bll.MenuItem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class AdministratorController {
    private AdministratorGUI administratorGUI;
    private DeliveryService deliveryService;
    private List<BaseProduct> list = new ArrayList<>();

    private enum TableType {
        MENU,   // represents the table containing all the menu items
        COMPOSITE   // represents the table containing only the items that make a composite product
    }

    public AdministratorController(AdministratorGUI administratorGUI, DeliveryService deliveryService) {
        this.administratorGUI = administratorGUI;
        this.deliveryService = deliveryService;
    }

    public void createAddBaseProductButtonActionListener(JButton addBtn) {
        addBtn.addActionListener(e -> deliveryService.addBaseProductToMenu(new BaseProduct(administratorGUI.getProductNameTxt().getText(),
                Float.parseFloat(administratorGUI.getRatingTxt().getText()),
                Integer.parseInt(administratorGUI.getCaloriesTxt().getText()),
                Integer.parseInt(administratorGUI.getProteinsTxt().getText()),
                Integer.parseInt(administratorGUI.getFatsTxt().getText()),
                Integer.parseInt(administratorGUI.getSodiumTxt().getText()),
                Float.parseFloat(administratorGUI.getPriceTxt().getText()))));
    }

    public void createRemoveProductButtonActionListener(JButton removeBtn) {
        removeBtn.addActionListener(e -> deliveryService.removeProduct(administratorGUI.getProductNameTxt().getText()));
    }

    public void createModifyProductButtonActionListener(JButton modifyBtn) {
        modifyBtn.addActionListener(e -> deliveryService.updateExistingProduct(administratorGUI.getProductNameTxt().getText(),
                Float.parseFloat(administratorGUI.getRatingTxt().getText()),
                Integer.parseInt(administratorGUI.getCaloriesTxt().getText()),
                Integer.parseInt(administratorGUI.getProteinsTxt().getText()),
                Integer.parseInt(administratorGUI.getFatsTxt().getText()),
                Integer.parseInt(administratorGUI.getSodiumTxt().getText()),
                Float.parseFloat(administratorGUI.getPriceTxt().getText())));
    }

    public void createAddToCompositeProductButtonActionListener(JButton addBtn) {
        addBtn.addActionListener(e -> configureJTable(administratorGUI.getCompositeProductTable(), new DefaultTableModel(), TableType.COMPOSITE, list));
    }

    public void createAddCompositeProductActionListener(JButton addBtn) {
        addBtn.addActionListener(e -> deliveryService.addCompositeProductToMenu(getCompositeProduct(list)));
    }

    public void createImportButtonActionListener(JButton importBtn) {
        importBtn.addActionListener(e -> configureJTable(administratorGUI.getMenuTable(), new DefaultTableModel(), TableType.MENU, null));
    }

    public void createMenuTableMouseListener(JTable menuTable) {
        menuTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                DefaultTableModel model = (DefaultTableModel) menuTable.getModel();

                administratorGUI.setProductNameTxt((String) model.getValueAt(menuTable.getSelectedRow(), 0));
                administratorGUI.setRatingTxt((String) model.getValueAt(menuTable.getSelectedRow(), 1));
                administratorGUI.setCaloriesTxt((String) model.getValueAt(menuTable.getSelectedRow(), 2));
                administratorGUI.setProteinsTxt((String) model.getValueAt(menuTable.getSelectedRow(), 3));
                administratorGUI.setFatsTxt((String) model.getValueAt(menuTable.getSelectedRow(), 4));
                administratorGUI.setSodiumTxt((String) model.getValueAt(menuTable.getSelectedRow(), 5));
                administratorGUI.setPriceTxt((String) model.getValueAt(menuTable.getSelectedRow(), 6));
            }
        });
    }

    private void configureJTable(JTable table, DefaultTableModel tableModel, TableType tableType, List<BaseProduct> list) {
        String[] columnNames = {"Title", "Rating", "Calories", "Proteins", "Fats", "Sodium", "Price"};

        for (String columnName : columnNames) {
            tableModel.addColumn(columnName);
        }

        if (tableType == TableType.MENU)
            importMenuItemsToTable(tableModel);
        else
            addItemToCompositeProduct(tableModel, list);
        table.setModel(tableModel);
    }

    private void importMenuItemsToTable(DefaultTableModel tableModel) {
        for (MenuItem mi : deliveryService.getMenu()) {
            tableModel.addRow(new Object[]{mi.getTitle(), String.valueOf(mi.getRating()), String.valueOf(mi.getCalories()),
                    String.valueOf(mi.getProteins()), String.valueOf(mi.getFats()), String.valueOf(mi.getSodium()), String.valueOf(mi.getPrice())});
        }
    }

    private CompositeProduct getCompositeProduct(List<BaseProduct> bpList) {
        CompositeProduct compositeProduct = new CompositeProduct(administratorGUI.getCompositeProductTxt().getText(), bpList);
        compositeProduct.getCompositeProductItems().add(new BaseProduct(administratorGUI.getProductNameTxt().getText(),
                Float.parseFloat(administratorGUI.getRatingTxt().getText()),
                Integer.parseInt(administratorGUI.getCaloriesTxt().getText()),
                Integer.parseInt(administratorGUI.getProteinsTxt().getText()),
                Integer.parseInt(administratorGUI.getFatsTxt().getText()),
                Integer.parseInt(administratorGUI.getSodiumTxt().getText()),
                Float.parseFloat(administratorGUI.getPriceTxt().getText())));

        return compositeProduct;
    }

    private void addItemToCompositeProduct(DefaultTableModel tableModel, List<BaseProduct> bpList) {
        for (BaseProduct bp : getCompositeProduct(bpList).getCompositeProductItems()) {
            tableModel.addRow(new Object[]{bp.getTitle(), String.valueOf(bp.getRating()), String.valueOf(bp.getCalories()),
                    String.valueOf(bp.getProteins()), String.valueOf(bp.getFats()), String.valueOf(bp.getSodium()), String.valueOf(bp.getPrice())});
        }
    }

    public void createGoBackBtnActionListener(JButton goBackBtn) {
        goBackBtn.addActionListener(e -> {
            new LoginGUI(deliveryService);
            administratorGUI.dispose();
        });
    }

    public void createGenerateReportsButtonActionListener(JButton generateBtn) {
        generateBtn.addActionListener(e -> {
            new ReportsGUI(deliveryService);
            administratorGUI.dispose();
        });
    }
}
