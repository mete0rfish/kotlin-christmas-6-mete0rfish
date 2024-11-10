package store.product.domain;

import store.product.dto.ProductReadResponse;
import store.product.view.InputView;

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
            reduceRegularStock(quantity);
            return new int[] {0, 0, quantity};
        }

        int buyAndGetCount = promotion.buyCount() + promotion.getCount();
        int maxPromoApplications = Math.min(quantity / buyAndGetCount, promotionStock / buyAndGetCount);

        int freeItems =  maxPromoApplications * promotion.getCount();
        int paidItems = maxPromoApplications * promotion.buyCount();
        int remainItems = quantity - (freeItems + paidItems);

        // promotion보다 적을 경우
        if(maxPromoApplications == 0) {
            int lessPromoItem = promotion.getCount();
            String input = InputView.inputLessPromotion(name, lessPromoItem);
            if(input.equals("Y")) {
                freeItems = lessPromoItem;
                paidItems = remainItems;
                remainItems = 0;
            }
        }

        // 프로모션 부족한 경우
        if(remainItems > promotionStock - freeItems - paidItems) {
            boolean isYes = handleLessPromotionStock(freeItems + paidItems, remainItems);
            if(isYes) {
                return new int[] {paidItems, freeItems, remainItems};
            }
            return new int[] {paidItems, freeItems, 0};
        }
        // 프로모션 낭낭한 경우

        reducePromotionStock(paidItems + freeItems);
        reduceRegularStock(remainItems);

        return new int[] {paidItems, freeItems, remainItems};
    }

    private boolean handleLessPromotionStock(int totalPromoItems, int remainItems) {
        reducePromotionStock(totalPromoItems);

        String input = InputView.inputNotPromotion(name, remainItems);
        if(input.equals("Y")) {
            reducePromotionStock(promotionStock);
            reduceRegularStock(remainItems-promotionStock);
            return true;
        }

        return false;
    }

    private synchronized void reducePromotionStock(int quantity) {
        if(quantity > promotionStock) {
            promotionStock = 0;
            return;
        }

        this.promotionStock -= quantity;
    }

    private synchronized void reduceRegularStock(int quantity) {
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
                            " " + getDisplayCount(promotionStock) +
                        " " + promotion.name() + "\n";
        }
        result += "- " + name +
                " " + price + "원" +
                " " + getDisplayCount(regularStock);
        return result;
    }

    private String getDisplayCount(int stock) {
        if(stock > 0) {
            return String.valueOf(stock) + "개";
        }
        return "재고 없음";
    }
}
