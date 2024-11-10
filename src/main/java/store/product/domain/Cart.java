package store.product.domain;

import store.product.dto.ProductFreeDTO;
import store.product.dto.ProductPaidDTO;
import store.product.dto.ProductReadResponse;
import store.product.dto.ProductRemainDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Cart {
    private final List<CartItem> items = new ArrayList<>();

    public void buyItem(Product product, int quantity) {
        int[] quantities = product.calculateFreeAndPaidStock(quantity);
        items.add(new CartItem(product, quantities[0], quantities[1], quantities[2]));
    }

    public List<CartItem> getItems() {
        return items.stream()
                .sorted((item1, item2) -> {
                    int sum1 = item1.getTotalQuantity();
                    int sum2 = item2.getTotalQuantity();
                    return Integer.compare(sum1, sum2);
                })
                .toList();
    }

    public int getTotalPrice() {
        return items.stream().mapToInt(CartItem::getTotalPrice).sum();
    }

    public List<ProductPaidDTO> getCartItemPaidDTOs() {
        List<ProductPaidDTO> paidDTOs = new ArrayList<>();
        items.forEach(cartItem -> {
            paidDTOs.add(cartItem.getPaidProduct());
        });
        return paidDTOs;
    }

    public List<ProductFreeDTO> getCartItemFreeDTOs() {
        List<ProductFreeDTO> freeDTOs = new ArrayList<>();
        items.forEach(cartItem -> {
            freeDTOs.add(cartItem.getFreeProduct());
        });
        return freeDTOs;
    }

    public List<ProductRemainDTO> getCartItemRemainDTOs() {
        List<ProductRemainDTO> remainDTOs = new ArrayList<>();
        items.forEach(cartItem -> {
            remainDTOs.add(cartItem.getRemainProduct());
        });
        return remainDTOs;
    }

    public int getTotalQuantity() {
        return items.stream().mapToInt(CartItem::getTotalQuantity).sum();
    }
}
