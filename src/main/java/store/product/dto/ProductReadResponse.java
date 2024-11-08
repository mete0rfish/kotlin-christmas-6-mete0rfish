package store.product.dto;

public record ProductReadResponse(
        String name,
        int price,
        int quantity,
        String promotion
) {

    @Override
    public String toString() {
        return "ProductReadResponse{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", promotion='" + promotion + '\'' +
                '}';
    }
}
