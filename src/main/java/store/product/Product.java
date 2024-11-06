package store.product;

import store.product.dto.ProductReadResponse;

public class Product {
    private final String name;
    private final int price;
    private int quantity;
    private final Promotion promotion;

    private Product(String name, int price, int quantity, Promotion promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
    }

    public static Product of(ProductReadResponse response, Promotion promotion) {
        return new Product(response.name(), response.price(), response.quantity(), promotion);
    }

    public void buy() {
        this.quantity--;
    }
}
