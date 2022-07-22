package bll;

import java.util.ArrayList;
import java.util.List;

public class CompositeProduct extends MenuItem {
    private float ratingSum;
    private List<BaseProduct> baseProductsList;

    public CompositeProduct(String title, List<BaseProduct> baseProductsList) {
        super(title);
        this.ratingSum = 0.0f;
        this.calories = 0;
        this.proteins = 0;
        this.fats = 0;
        this.sodium = 0;
        this.baseProductsList = baseProductsList;

        for (BaseProduct baseProduct : baseProductsList) {
            this.ratingSum += baseProduct.getRating();
            this.calories += baseProduct.getCalories();
            this.proteins += baseProduct.getProteins();
            this.fats += baseProduct.getFats();
            this.sodium += baseProduct.getSodium();
            this.price = computePrice();
        }
        this.rating = ratingSum / baseProductsList.size();
    }

    @Override
    public float computePrice() {
        float compositeProductPrice = 0.0f;

        for (BaseProduct baseProduct : baseProductsList) {
            compositeProductPrice += baseProduct.computePrice();
        }

        return compositeProductPrice;
    }

    public List<BaseProduct> getCompositeProductItems() {
        return this.baseProductsList;
    }
}
