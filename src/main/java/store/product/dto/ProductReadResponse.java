package store.product.dto;

import store.product.Promotion;

public record ProductReadResponse(
        String name,
        int price,
        int quantity,
        String promotion
) {

}
