package presentation;

import bll.DeliveryService;

import javax.swing.*;

public class EmployeeGUI extends JFrame {

    private JPanel employeePanel;
    private JTextArea notificationsTA;
    private JButton goBackBtn;
    private EmployeeController employeeController;
    private DeliveryService deliveryService;

    public EmployeeGUI(DeliveryService deliveryService) {
        super("Employee Window");
        setContentPane(employeePanel);
        setSize(550, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        this.deliveryService = deliveryService;
        employeeController = new EmployeeController(this, deliveryService);
        employeeController.createGoBackBtnActionListener(goBackBtn);
    }
    public JTextArea getNotificationsTA() {
        return notificationsTA;
    }
}
