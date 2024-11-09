package store.product.domain;

import store.product.dto.ProductReadResponse;

public class Product {
    private final String name;
    private final int price;
    private int promotionStock;
    private int regularStock;
    private Promotion promotion;

    public Product(String name, int price, int promotionStock) {
        this.name = name;
        this.price = price;
        this.promotionStock = promotionStock;
    }

    public Product(String name, int price, Promotion promotion, int regularStock, int promotionStock) {
        this.name = name;
        this.price = price;
        this.promotion = promotion;
        this.regularStock = regularStock;
        this.promotionStock = promotionStock;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }

    public void setRegularStock(int regularStock) {
        this.regularStock = regularStock;
    }

    public void setPromotionStock(int promotionStock) {
        this.promotionStock = promotionStock;
    }

    public static Product of(ProductReadResponse response) {
        return new Product(response.name(), response.price(), response.quantity());
    }

    public int[] calculateFreeAndPaidStock(int quantity) {
        if (promotion == null) {
            return new int[] {0, quantity};
        }

        int buyAndGetCount = promotion.buyCount() + promotion.getCount();
        int maxPromoApplications = Math.min(quantity / buyAndGetCount, promotionStock / promotion.getCount());
        int freeItems =  maxPromoApplications * promotion.getCount();

        int paidItems = quantity - freeItems;
        if(freeItems > 0) {
            reducePromotionStock(freeItems);
        }
        if(paidItems > promotionStock) {
            int remainingPaidStock = paidItems - promotionStock;
            reducePromotionStock(promotionStock);
            reduceRegularStock(remainingPaidStock);
            return new int[] {freeItems, paidItems};
        } else {
            reducePromotionStock(paidItems);
            return new int[] {freeItems, paidItems};
        }
    }

    private void reducePromotionStock(int quantity) {
        if(quantity <= promotionStock) {
            promotionStock -= quantity;
            return;
        }
        throw new IllegalArgumentException("프로모션 재고가 부족합니다.");
    }

    private void reduceRegularStock(int quantity) {
        if(quantity <= regularStock) {
            regularStock -= quantity;
            return;
        }
        throw new IllegalArgumentException("일반 재고가 부족합니다.");
    }

    public String getName() {
        return this.name;
    }

    public int getPrice() {
        return price;
    }

    public int getRegularStock() {
        return regularStock;
    }

    public int getPromotionStock() {
        return promotionStock;
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
