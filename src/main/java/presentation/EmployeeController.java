package presentation;

import bll.DeliveryService;
import bll.MenuItem;
import bll.Order;

import javax.swing.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

public class EmployeeController implements Observer {
    private EmployeeGUI employeeGUI;
    private DeliveryService deliveryService;

    public EmployeeController(EmployeeGUI employeeGUI, DeliveryService deliveryService) {
        this.employeeGUI = employeeGUI;
        this.deliveryService = deliveryService;

        update(deliveryService, deliveryService.getOrderMenuItemsMap());
    }

    public void createGoBackBtnActionListener(JButton goBackBtn) {
        goBackBtn.addActionListener(e -> {
            new LoginGUI(deliveryService);
            employeeGUI.dispose();
        });
    }

    @Override
    public void update(Observable o, Object arg) {
        Map<Order, List<MenuItem>> placedOrders = (Map<Order, List<MenuItem>>) arg;

        StringBuilder stringBuilder = new StringBuilder();

        for (Order ord : placedOrders.keySet()) {
            stringBuilder.append("\nOrder #" + ord.getOrderID() + ", placed by Client " + ord.getClient().getAccountID() + ":\n\n");
            float orderTotal = 0.0f;
            for (MenuItem mi : ord.getOrderedItems()) {
                stringBuilder.append("   " + mi.getTitle() + " x1................. $" + mi.getPrice() + "\n");
                orderTotal += mi.getPrice();
            }
            stringBuilder.append("\nTotal: $" + orderTotal + "\n");
            stringBuilder.append("Order placed at date: " + DateTimeFormatter.ISO_LOCAL_DATE.format(ord.getOrderDate()) + "\n");
            stringBuilder.append("-----------------------------------------------------------------------------------------------------");
        }

        String existingText = employeeGUI.getNotificationsTA().getText();
        employeeGUI.getNotificationsTA().setText(existingText + stringBuilder);
    }
}
