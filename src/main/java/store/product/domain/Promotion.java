package store.product.domain;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

import camp.nextstep.edu.missionutils.DateTimes;
import store.product.dto.PromotionReadResponse;

import static store.util.DateFormat.formatter;

public record Promotion(
        String name,
        int buyCount,
        int getCount,
        LocalDate startDate,
        LocalDate endDate
) {

    public Promotion findByName(String name) {
        if(Objects.equals(this.name, name)) {
            return this;
        }
        return null;
    }

    public boolean validateExpiration() {
        LocalDate currentTime = DateTimes.now().toLocalDate();
        if (currentTime.isBefore(startDate) || currentTime.isAfter(endDate)) {
            return true;
        }
        return false;
    }

    public static Promotion of(PromotionReadResponse response) {
        LocalDate startDate = null;
        LocalDate endDate = null;
        startDate = LocalDate.parse(response.start_date(), formatter);
        endDate = LocalDate.parse(response.end_date(), formatter);
        return new Promotion(
                response.name(),
                response.buy(),
                response.get(),
                startDate,
                endDate
        );
    }
}
