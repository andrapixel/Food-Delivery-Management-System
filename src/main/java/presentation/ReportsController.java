package presentation;

import bll.DeliveryService;

import javax.swing.*;
import java.time.LocalDate;

public class ReportsController {
    private ReportsGUI reportsGUI;
    private DeliveryService deliveryService;
    public ReportsController(ReportsGUI reportsGUI, DeliveryService deliveryService) {
        this.reportsGUI = reportsGUI;
        this.deliveryService = deliveryService;
    }

    public void createFirstReportButtonActionListener(JButton button) {
        button.addActionListener(e -> deliveryService.generateTimeIntervalReport(Integer.parseInt(reportsGUI.getStartHourTxt().getText()),
                Integer.parseInt(reportsGUI.getEndHourTxt().getText())));
    }

    public void createSecondReportButtonActionListener(JButton button) {
        button.addActionListener(e -> deliveryService.generateMostOrderedProductsReport(Integer.parseInt(reportsGUI.getNrOrdersTxt().getText())));
    }

    public void createThirdReportButtonActionListener(JButton button) {
        button.addActionListener(e -> deliveryService.generateHighestPaymentsReport(Integer.parseInt(reportsGUI.getNrOrdersTxt().getText()),
                Float.parseFloat(reportsGUI.getPaymentAmountTxt().getText())));
    }

    public void createFourthReportButtonActionListener(JButton button) {
        button.addActionListener(e -> deliveryService.generateNrTimesAnItemWasOrderedInADayReport(LocalDate.parse(reportsGUI.getDateTxt().getText())));
    }

    public void createGoBackBtnActionListener(JButton goBackBtn) {
        goBackBtn.addActionListener(e -> {
            new AdministratorGUI(deliveryService);
            reportsGUI.dispose();
        });
    }
}
