package store.product.dto;

import store.product.domain.Product;

public record ProductFreeDTO(
        String name,
        int freeCount,
        int price
) {

}
