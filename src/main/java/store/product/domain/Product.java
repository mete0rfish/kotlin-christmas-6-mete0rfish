package store.product.domain;

import store.product.dto.ProductReadResponse;

public class Product {
    private final String name;
    private final int price;
    private int promotionStock;
    private int regularStock;
    private final Promotion promotion;

    public Product(String name, int price, int promotionStock, Promotion promotion) {
        this.name = name;
        this.price = price;
        this.promotionStock = promotionStock;
        this.promotion = promotion;
    }

    public Product(String name, int price, Promotion promotion, int regularStock, int promotionStock) {
        this.name = name;
        this.price = price;
        this.promotion = promotion;
        this.regularStock = regularStock;
        this.promotionStock = promotionStock;
    }

    public void setRegularStock(int regularStock) {
        this.regularStock = regularStock;
    }

    public static Product of(ProductReadResponse response, Promotion promotion) {
        return new Product(response.name(), response.price(), response.quantity(), promotion);
    }

    public void reducePromotionStock(int quantity) {
        if(quantity <= promotionStock) {
            promotionStock -= quantity;
            return;
        }
        throw new IllegalArgumentException("프로모션 재고가 부족합니다.");
    }

    public void reduceRegularStock(int quantity) {
        if(quantity <= regularStock) {
            regularStock -= quantity;
            return;
        }
        throw new IllegalArgumentException("일반 재고가 부족합니다.");
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        String result = "";
        if(this.promotion != null) {
            result += "- " + name +
                            " " + price + "원" +
                            " " + promotionStock + "개" +
                        " " + promotion.name() + "\n";
        }
        result += "- " + name +
                " " + price + "원" +
                " " + regularStock + "개";
        return result;
    }
}
