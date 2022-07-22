package presentation;

import bll.DeliveryService;

import javax.swing.*;

public class ReportsGUI extends JFrame {
    private JPanel reportsPanel;
    private JTextField startHourTxt;
    private JTextField endHourTxt;
    private JButton genTimeIntervalReportBtn;
    private JTextField nrOrdersTxt;
    private JButton genMostOrderedReportBtn;
    private JTextField paymentAmountTxt;
    private JButton genHighestPaymentsReportBtn;
    private JButton genDailyItemReportBtn;
    private JButton goBackBtn;
    private JTextField dateTxt;
    private ReportsController reportsController;
    private DeliveryService deliveryService;

    public ReportsGUI(DeliveryService deliveryService) {
        super("Reports Window");
        setContentPane(reportsPanel);
        setSize(650, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        this.deliveryService = deliveryService;

        this.reportsController = new ReportsController(this, deliveryService);
        reportsController.createGoBackBtnActionListener(goBackBtn);
        reportsController.createFirstReportButtonActionListener(genTimeIntervalReportBtn);
        reportsController.createSecondReportButtonActionListener(genMostOrderedReportBtn);
        reportsController.createFourthReportButtonActionListener(genDailyItemReportBtn);
        reportsController.createThirdReportButtonActionListener(genHighestPaymentsReportBtn);
    }

    public JTextField getStartHourTxt() {
        return startHourTxt;
    }

    public JTextField getEndHourTxt() {
        return endHourTxt;
    }

    public JTextField getNrOrdersTxt() {
        return nrOrdersTxt;
    }

    public JTextField getPaymentAmountTxt() {
        return paymentAmountTxt;
    }

    public JTextField getDateTxt() {
        return dateTxt;
    }
}
