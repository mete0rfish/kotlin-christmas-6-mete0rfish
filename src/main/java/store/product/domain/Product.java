package store.product.domain;

import store.product.dto.ProductReadResponse;
import store.product.view.InputView;

import java.text.DecimalFormat;

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

    public void validateProductRange(int count) {
        if(count < 0) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }
        if(count > promotionStock) {
            throw new IllegalArgumentException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
        }
    }

    public int[] calculateFreeAndPaidStock(int quantity) {
        if (promotion == null || promotion.validateExpiration()) {
            reduceRegularStock(quantity);
            return new int[] {0, 0, quantity};
        }

        int[] stockDetails = calculatePromoStockDetails(quantity);
        int freeItems =  stockDetails[0];
        int paidItems = stockDetails[1];
        int remainItems = stockDetails[2];

        if(stockDetails[3] == 0 && handleInsufficientPromotionItems(freeItems, remainItems)) {
            return new int[] {paidItems, freeItems, remainItems};
        }

        updateStocks(paidItems, freeItems, remainItems);
        return new int[] {paidItems, freeItems, remainItems};
    }

    private int[] calculatePromoStockDetails(int quantity) {
        int buyAndGetCount = promotion.buyCount() + promotion.getCount();
        int maxPromoApplications = Math.min(quantity / buyAndGetCount, promotionStock / buyAndGetCount);
        int freeItems = maxPromoApplications * promotion.getCount();
        int paidItems = maxPromoApplications * promotion.buyCount();
        int remainItems = quantity - (freeItems + paidItems);

        return new int[] {freeItems, paidItems, remainItems, maxPromoApplications};
    }

    private boolean handleInsufficientPromotionItems(int freeItems, int remainItems) {
        if(remainItems > promotionStock - freeItems) {
            return handleLessPromotionStock(freeItems, remainItems);
        }
        return false;
    }

    private void updateStocks(int paidItems, int freeItems, int remainItems) {
        reducePromotionStock(paidItems + freeItems);
        reduceRegularStock(remainItems);
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
                            " " + getDisplayPrice(price) + "원" +
                            " " + getDisplayCount(promotionStock) +
                        " " + promotion.name() + "\n";
        }
        result += "- " + name +
                " " + getDisplayPrice(price) + "원" +
                " " + getDisplayCount(regularStock);
        return result;
    }

    private String getDisplayCount(int stock) {
        if(stock > 0) {
            return String.valueOf(stock) + "개";
        }
        return "재고 없음";
    }

    private String getDisplayPrice(int price) {
        DecimalFormat df = new DecimalFormat("###,###");
        return df.format(price);
    }
}
