package store.product.domain;

import store.product.dto.ProductFreeDTO;
import store.product.dto.ProductPaidDTO;
import store.product.dto.ProductRemainDTO;

public class CartItem {
    private Product product;
    private int freeCount;
    private int paidCount;
    private int remainCount;

    public CartItem(Product product, int paidCount, int freeCount, int remainCount) {
        this.product = product;
        this.freeCount = freeCount;
        this.paidCount = paidCount;
        this.remainCount = remainCount;
    }

    public ProductFreeDTO getFreeProduct() {
        return new ProductFreeDTO(
                product.getName(),
                freeCount,
                product.getPrice() * freeCount
        );
    }

    public ProductPaidDTO getPaidProduct() {
        return new ProductPaidDTO(
                product.getName(),
                paidCount,
                product.getPrice() * paidCount
        );
    }

    public ProductRemainDTO getRemainProduct() {
        return new ProductRemainDTO(
                product.getName(),
                remainCount,
                product.getPrice() * remainCount
        );
    }
    public String getProductName() {
        return product.getName();
    }

    public int getTotalPrice() {
        return product.getPrice() * freeCount + product.getPrice() * paidCount + product.getPrice() * remainCount;
    }

    public int getTotalQuantity() {
        return freeCount + paidCount + remainCount;
    }
}
