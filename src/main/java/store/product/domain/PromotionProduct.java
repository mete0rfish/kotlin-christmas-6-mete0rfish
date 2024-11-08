package store.product.domain;

import store.product.dto.ProductReadResponse;

public class PromotionProduct extends Product {
    private final Promotion promotion;

    public PromotionProduct(String name, int price, int quantity, Promotion promotion) {
        super(name, price, quantity);
        this.promotion = promotion;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("- ").append(name)
                .append(" ").append(price).append("원")
                .append(" ").append(quantity).append("개");

        if(promotion != null) {
            builder.append(" ").append(promotion);
        }
        return builder.toString();
    }

    public static Product of(ProductReadResponse response, Promotion promotion) {
        return new PromotionProduct(response.name(), response.price(), response.quantity(), promotion);
    }
}
