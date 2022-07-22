package start;

import bll.Account;
import bll.DeliveryService;
import bll.UserType;
import dao.Serializator;
import presentation.LoginGUI;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Serializator serializator = new Serializator();
        DeliveryService deliveryService = serializator.deserializeObject("serialization.txt");

        if (deliveryService == null)
            deliveryService = new DeliveryService();

        /*Account adminAcc = new Account("admin", new char[]{'a', 'd', 'm', 'i', 'n'}, UserType.ADMINISTRATOR);
        Account employeeAcc = new Account("employee", new char[]{'e', 'm', 'p', 'a', 's', 's'}, UserType.EMPLOYEE);
        deliveryService.getRegisteredAccounts().add(adminAcc);
        deliveryService.getRegisteredAccounts().add(employeeAcc);
        serializator.serializeObject(deliveryService, "serialization.txt");*/

        deliveryService.displayAccounts();
        System.out.println();
        new LoginGUI(deliveryService);
    }
}
