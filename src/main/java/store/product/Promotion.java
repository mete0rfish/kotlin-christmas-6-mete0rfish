package store.product;

import java.text.ParseException;
import java.util.Date;
import java.util.Objects;

import camp.nextstep.edu.missionutils.*;
import store.product.dto.PromotionReadResponse;

import static store.util.DateFormat.formatter;

public record Promotion(
        String name,
        int buyCount,
        int getCount,
        Date startDate,
        Date endDate
) {

    public Promotion findByName(String name) {
        if(Objects.equals(this.name, name)) {
            return this;
        }
        return null;
    }

    public Promotion of(PromotionReadResponse response) {
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = formatter.parse(response.start_date());
            endDate = formatter.parse(response.end_date());
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            // TODO 커스텀 예외
        }
        return new Promotion(
                response.name(),
                response.buy(),
                response.get(),
                startDate,
                endDate
        );
    }
}
