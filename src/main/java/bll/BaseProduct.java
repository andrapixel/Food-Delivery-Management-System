package bll;

public class BaseProduct extends MenuItem {
    public BaseProduct() {
        super();
    }

    public BaseProduct(String title, float rating, int calories, int proteins, int fats, int sodium, float price) {
        super(title, rating, calories, proteins, fats, sodium, price);
    }

    @Override
    public float computePrice() {
        return this.price;
    }
}
