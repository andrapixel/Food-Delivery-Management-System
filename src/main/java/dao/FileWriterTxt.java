package dao;

import bll.MenuItem;
import bll.Order;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class FileWriterTxt implements Serializable {

    public void writeTimeIntervalReport(List<Order> filteredOrders, int startHour, int endHour) {
        File reportFile = new File(getReportPath(1));
        if (reportFile.exists()) {
            reportFile.delete();
            reportFile = new File(getReportPath(1));
        }

        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(reportFile));

            bufferedWriter.write("The orders placed between " + startHour + ":00 and " + endHour + ":00 are the following:\n\n" );
            for (Order order : filteredOrders) {
                bufferedWriter.write("Order #" + order.getOrderID() + ", placed at " + order.getOrderDate().getHour() + ":" +
                        order.getOrderDate().getMinute() + ";");
                bufferedWriter.newLine();
            }

            bufferedWriter.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeNrOrderedItemsInADayReport(List<MenuItem> items, LocalDate date) {
        File reportFile = new File(getReportPath(4));
        if (reportFile.exists()) {
            reportFile.delete();
            reportFile = new File(getReportPath(4));
        }

        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(reportFile));

            bufferedWriter.write("The items ordered on " + date + " are:\n\n" );
            for (MenuItem mi : items) {
                bufferedWriter.write(mi.getTitle());
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void generateOrderBill(Order order) {
        File billFile = new File(getBillPath(order));
        if (billFile.exists()) {
            billFile.delete();
            billFile = new File(getBillPath(order));
        }

        writeOrderBill(billFile, order);
    }

    private String getReportPath(int reportType) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("./Reports/Report_Type" + reportType);
        stringBuilder.append(".txt");

        return stringBuilder.toString();
    }

    private String getBillPath(Order order) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("./OrderBills/Bill_Order#");
        stringBuilder.append(order.getOrderID());
        stringBuilder.append(".txt");

        return stringBuilder.toString();
    }

    private void writeOrderBill(File billFile, Order order) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new java.io.FileWriter(billFile));

            bufferedWriter.write("Order #" + order.getOrderID() + " Bill\n\n\n");
            bufferedWriter.write("Placed by client " + order.getClient().getAccountID() + "\n\n");
            float orderTotal = 0.0f;
            for (MenuItem mi : order.getOrderedItems()) {//placedOrders.get(ord)
                bufferedWriter.write("   " + mi.getTitle() + " x1................. $" + mi.getPrice() + "\n");
                orderTotal += mi.getPrice();
            }
            bufferedWriter.write("\n--------------------------------------------------------------------------------------------------");
            bufferedWriter.write("\nTotal: $" + orderTotal + "\n");
            bufferedWriter.write("Date: " + DateTimeFormatter.ISO_LOCAL_DATE.format(order.getOrderDate()));

            bufferedWriter.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //public void writeMostOrderedItemReport(List<MenuItem> filteredItems, int nrOrders) {
        /*File reportFile = new File(getReportPath(2));
        if (reportFile.exists()) {
            reportFile.delete();
            reportFile = new File(getReportPath(2));
        }

        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(reportFile));
            //DeliveryService ds = new DeliveryService();
            bufferedWriter.write("The menu items that were ordered more than " + nrOrders + " times so far are:\n\n" );
            for (MenuItem mi : filteredItems) {
                System.out.println(mi.getTitle() + "---" + mi.getOrderedQuantity());
                bufferedWriter.write(mi.getTitle() + ", which was ordered " + ds.getItemOrderedQuantity(mi) + " times;");
                bufferedWriter.newLine();
            }

            bufferedWriter.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }*/
    //  }

    // public void writeHighestPaymentsReport(List<Account> accounts, int nrTimes, float amount) {}
       /* File reportFile = new File(getReportPath(3));
        if (reportFile.exists()) {
            reportFile.delete();
            reportFile = new File(getReportPath(3));
        }

        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(reportFile));

            bufferedWriter.write("The clients that ordered more than " + nrTimes + " times and the value of their orders " +
                    "was higher than $" + amount + ":\n\n" );
            for (Account account : accounts) {
                float billPrice = 0.0f;
                bufferedWriter.write("Client " + account.getUsername() + "(" + account.getAccountID() + "), who paid $");}*/
                /*for (MenuItem mi : order.getOrderedItems()) {
                    billPrice += mi.getPrice();
                }
                bufferedWriter.write(billPrice + ";");
                bufferedWriter.newLine();
            }

            bufferedWriter.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }*/
}
