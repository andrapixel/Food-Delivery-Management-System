package bll;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class Order implements Serializable {
    private int orderID;
    private int clientID;
    private LocalDateTime orderDate;
    private List<MenuItem> orderedItems;
    private Account client;

    public Order(int orderID, Account client, List<MenuItem> orderedItems) {
        this.orderID = orderID;
        this.client = client;
        this.orderedItems = orderedItems;
        orderDate = LocalDateTime.now();
    }

    public Account getClient() {
        return client;
    }

    public void setClient(Account client) {
        this.client = client;
    }

    public List<MenuItem> getOrderedItems() {
        return orderedItems;
    }

    public int getOrderID() {
        return orderID;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }


    public float getOrderPrice() {
        float total = 0.0f;
        for (MenuItem mi : orderedItems) {
            total += mi.getPrice();
        }

        return total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderID == order.orderID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderID);
    }
}
