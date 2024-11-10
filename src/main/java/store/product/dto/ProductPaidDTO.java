package store.product.dto;

public record ProductPaidDTO(
        String name,
        int paidCount,
        int price
) {

}
