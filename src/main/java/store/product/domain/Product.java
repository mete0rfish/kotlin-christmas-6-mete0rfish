package store.product.domain;

import store.product.dto.ProductReadResponse;

public class Product {
    protected final String name;
    protected final int price;
    protected int quantity;

    Product(String name, int price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public static Product of(ProductReadResponse response) {
        return new Product(response.name(), response.price(), response.quantity());
    }

    public void buy() {
        this.quantity--;
    }

    @Override
    public String toString() {
        return "- " + name +
                " " + price + "원" +
                " " + quantity + "개";
    }
}
