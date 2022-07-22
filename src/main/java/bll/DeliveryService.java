package bll;


import dao.FileWriterTxt;
import dao.Serializator;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class DeliveryService extends Observable implements IDeliveryServiceProcessing, Serializable {
    private static final long serialVersionUID = 1234567L;
    private Map<Order, List<MenuItem>> orderMenuItemsMap; // stores the order related info
    private List<MenuItem> menuItemsCollection;   // stores all the menu items provided by the catering company
    private List<Account> registeredAccounts;
    private FileWriterTxt fileWriterTxt = new FileWriterTxt();
    private Serializator serializator = new Serializator();

    public DeliveryService() {
        this.menuItemsCollection = new ArrayList<>();
        this.orderMenuItemsMap = new HashMap<>();
        this.registeredAccounts = new ArrayList<>();

        try {
            for (BaseProduct bp : importProductsFromCsvFile("./menu.csv")) {
                menuItemsCollection.add(bp);
            }
            serializator.serializeObject(this, "serialization.txt");
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    // Administrator Operations implementation
    @Override
    public List<BaseProduct> importProductsFromCsvFile(String csvPath) throws IOException {
        try (BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(csvPath))) {
            String firstLine = bufferedReader.readLine();   // go over the first line
            if (firstLine == null)
                throw new IOException("Empty CSV file.");

            return bufferedReader.lines()
                    .map(line -> {
                        String[] tokens = line.split(",");

                        return new BaseProduct(tokens[0], Float.parseFloat(tokens[1]), Integer.parseInt(tokens[2]),
                                Integer.parseInt(tokens[3]), Integer.parseInt(tokens[4]), Integer.parseInt(tokens[5]),
                                Float.parseFloat(tokens[6]));
                    })
                    .distinct()
                    .collect(Collectors.toList());
        }
    }

    // TODO: well-formed method
    // TODO: asserts, pre/postconditions, invariant
    @Override
    public void addBaseProductToMenu(BaseProduct newBaseProduct) {
        menuItemsCollection.add(0, newBaseProduct);
        serializator.serializeObject(this, "serialization.txt");
    }

    @Override
    public void addCompositeProductToMenu(CompositeProduct newCompositeProduct) {
        menuItemsCollection.add(0, newCompositeProduct);
        serializator.serializeObject(this, "serialization.txt");
    }

    @Override
    public void updateExistingProduct(String title, float rating, int calories, int proteins, int fats, int sodium,
                                      float price) {
        MenuItem itemToBeUpdated = null;
        for (MenuItem item : menuItemsCollection) {
            if (item.getTitle().equals(title)) {
                itemToBeUpdated = item;
            }
        }

        itemToBeUpdated.setRating(rating);
        itemToBeUpdated.setCalories(calories);
        itemToBeUpdated.setProteins(proteins);
        itemToBeUpdated.setFats(fats);
        itemToBeUpdated.setSodium(sodium);
        itemToBeUpdated.setPrice(price);
        serializator.serializeObject(this, "serialization.txt");
    }

    @Override
    public void removeProduct(String title) {
        MenuItem itemToBeRemoved = null;
        for (MenuItem item : menuItemsCollection) {
            if (item.getTitle().equals(title))
                itemToBeRemoved = item;
        }

        menuItemsCollection.remove(itemToBeRemoved);
        serializator.serializeObject(this, "serialization.txt");
    }

    @Override
    public void generateTimeIntervalReport(int startTime, int endTime) {
        List<Order> result = orderMenuItemsMap.keySet().stream()
                .filter(order -> order.getOrderDate().getHour() >= startTime && order.getOrderDate().getHour() <= endTime)
                .collect(Collectors.toList());

        fileWriterTxt.writeTimeIntervalReport(result, startTime, endTime);
        System.out.println("Report generated successfully");
    }

    @Override
    public void generateMostOrderedProductsReport(int specifiedNrOfOrders) {
        File reportFile = new File("./Reports/Report_Type2.txt");
        if (reportFile.exists()) {
            reportFile.delete();
            reportFile = new File("./Reports/Report_Type2.txt");
        }

        List<MenuItem> result = menuItemsCollection.stream()
                .filter(menuItem -> getItemOrderedQuantity(menuItem) > specifiedNrOfOrders)
                .collect(Collectors.toList());

        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(reportFile));
            bufferedWriter.write("The menu items that were ordered more than " + specifiedNrOfOrders + " times so far are:\n\n" );
            for (MenuItem mi : result) {
                System.out.println(mi.getTitle() + "---" + mi.getOrderedQuantity());
                bufferedWriter.write(mi.getTitle() + ", which was ordered " + getItemOrderedQuantity(mi) + " times;");
                bufferedWriter.newLine();
            }

            bufferedWriter.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        //fileWriterTxt.writeMostOrderedItemReport(result, specifiedNrOfOrders);
        System.out.println("Report generated successfully");
    }

    @Override
    public void generateHighestPaymentsReport(int nrTimes, float specifiedPaymentAmount) {
        List<Order> orders = orderMenuItemsMap.keySet().stream().filter(order -> order.getOrderPrice() > specifiedPaymentAmount).
                collect(Collectors.toList());

        List<Account> clients = new ArrayList<>();
        orders.stream().filter(order -> findByUsername(order.getClient().getUsername()).getPlacedOrders() > nrTimes).
                forEach(order -> clients.add(findByUsername(order.getClient().getUsername())));

        File reportFile = new File("./Reports/Report_Type3.txt");
        if (reportFile.exists()) {
            reportFile.delete();
            reportFile = new File("./Reports/Report_Type3.txt");
        }

        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(reportFile));
            bufferedWriter.write("The clients that ordered more than " + nrTimes + " times and the value of their orders " +
                    "was higher than $" + specifiedPaymentAmount + ":\n\n" );
            for (Account account : clients) {
                bufferedWriter.write("Client " + account.getFirstName() + "(" + account.getAccountID() + ");");
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        //fileWriterTxt.writeHighestPaymentsReport(clients.stream().distinct().collect(Collectors.toList()), nrTimes, specifiedPaymentAmount);
        System.out.println("Report generated successfully");
    }

    @Override
    public void generateNrTimesAnItemWasOrderedInADayReport(LocalDate day) {
        List<Order> filteredOrders = orderMenuItemsMap.keySet().stream()
                .filter(order -> order.getOrderDate().toLocalDate().equals(day))
                .collect(Collectors.toList());

        List<MenuItem> result = new ArrayList<>();
        filteredOrders.stream().forEach(order -> result.addAll(orderMenuItemsMap.get(order)));

        fileWriterTxt.writeNrOrderedItemsInADayReport(result.stream().distinct().collect(Collectors.toList()), day);
        System.out.println("Report generated successfully");
    }

    // Client Operations implementation
    @Override
    public List<MenuItem> getMenu() {
        try {
            serializator.deserializeObject("serialization.txt");
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return menuItemsCollection;
    }

    @Override
    public List<MenuItem> searchProduct(SearchCriteria searchCriteria, String searchedField, List<MenuItem> list) {
        switch (searchCriteria) {
            case KEYWORD:
                List<MenuItem> menuItemsWithGivenKeyword = list
                        .stream()
                        .filter(item -> item.getTitle().contains(searchedField))
                        .collect(Collectors.toList());
                return menuItemsWithGivenKeyword;
            case RATING:
                List<MenuItem> menuItemsWithGivenRating = list
                        .stream()
                        .filter(item -> item.getRating() == Float.parseFloat(searchedField))
                        .collect(Collectors.toList());
                return menuItemsWithGivenRating;
            case CALORIES:
                List<MenuItem> menuItemsWithGivenCalories = list
                        .stream()
                        .filter(item -> item.getCalories() == Integer.parseInt(searchedField))
                        .collect(Collectors.toList());
                return menuItemsWithGivenCalories;
            case PROTEINS:
                List<MenuItem> menuItemsWithGivenProteins = list
                        .stream()
                        .filter(item -> item.getProteins() == Integer.parseInt(searchedField))
                        .collect(Collectors.toList());
                return menuItemsWithGivenProteins;
            case FATS:
                List<MenuItem> menuItemsWithGivenFats = list
                        .stream()
                        .filter(item -> item.getFats() == Integer.parseInt(searchedField))
                        .collect(Collectors.toList());
                return menuItemsWithGivenFats;
            case SODIUM:
                List<MenuItem> menuItemsWithGivenSodium = list
                        .stream()
                        .filter(item -> item.getSodium() == Integer.parseInt(searchedField))
                        .collect(Collectors.toList());
                return menuItemsWithGivenSodium;
            case PRICE:
                List<MenuItem> menuItemsWithGivenPrice = list
                        .stream()
                        .filter(item -> item.getPrice() == Float.parseFloat(searchedField))
                        .collect(Collectors.toList());
                return menuItemsWithGivenPrice;
        }

        return null;
    }

    @Override
    public void createOrder(Account client, List<MenuItem> orderedItemsList) {
        Order newOrder = new Order(orderMenuItemsMap.size() + 1, client, orderedItemsList);
        orderMenuItemsMap.put(newOrder, newOrder.getOrderedItems());
        findByUsername(client.getUsername()).increment();

        // notify employee that a new order has been placed
        setChanged();
        notifyObservers(orderMenuItemsMap);
        serializator.serializeObject(this, "serialization.txt");

        // generate the order bill
        fileWriterTxt.generateOrderBill(newOrder);
        System.out.println("Bill was generated successfully");
    }

    public Map<Order, List<MenuItem>> getOrderMenuItemsMap() {
        return orderMenuItemsMap;
    }

    // other useful methods
    public List<Account> getRegisteredAccounts() {
        return registeredAccounts;
    }

    public void displayAccounts() {
        for (Account acc : registeredAccounts) {
            System.out.println(acc.getUsername());
            System.out.println(acc.getAccountID());
        }
    }

    public int getItemOrderedQuantity(MenuItem menuItem) {
        int cnt = 0, totalCnt = 0;
        for (List<MenuItem> list : orderMenuItemsMap.values()) {
            cnt = 0;
            for (MenuItem mi : list) {
                if (menuItem.getTitle().equals(mi.getTitle()))
                    cnt++;
            }
            totalCnt += cnt;
        }

        return totalCnt;
    }

    public Account findByUsername(String username){
        return registeredAccounts.stream().filter(user -> user.getUsername().equals(username)).findAny().orElse(null);
    }
}
