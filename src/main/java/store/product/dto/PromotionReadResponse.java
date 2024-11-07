package store.product.dto;

import java.util.Date;

public record PromotionReadResponse(
        String name,
        int buy,
        int get,
        String start_date,
        String end_date
        ) {

    @Override
    public String toString() {
        return "PromotionReadResponse{" +
                "name='" + name + '\'' +
                ", buy=" + buy +
                ", get=" + get +
                ", start_date=" + start_date +
                ", end_date=" + end_date +
                '}';
    }
}
