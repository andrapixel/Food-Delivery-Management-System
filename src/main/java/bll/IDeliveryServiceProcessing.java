package bll;

import java.awt.*;
import java.io.IOException;
import java.sql.Time;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface IDeliveryServiceProcessing {
    // Administrator Operations
    List<BaseProduct> importProductsFromCsvFile(String csvPath) throws IOException;
    void addBaseProductToMenu(BaseProduct newBaseProduct);
    void addCompositeProductToMenu(CompositeProduct newCompositeProduct);
    void updateExistingProduct(String title, float rating, int calories, int proteins, int fats, int sodium, float price);
    void removeProduct(String title);
    void generateTimeIntervalReport(int startTime, int endTime);
    void generateMostOrderedProductsReport(int specifiedNrOfOrders);
    void generateHighestPaymentsReport(int nrTimes, float specifiedPaymentAmount);
    void generateNrTimesAnItemWasOrderedInADayReport(LocalDate date);


    // Client Operations
    List<MenuItem> getMenu();   // maybe not
    List<MenuItem> searchProduct(SearchCriteria searchCriteria, String searchedField, List<MenuItem> list);
    void createOrder(Account client, List<MenuItem> orderedItemsList);    // computes price + generates bill
}
