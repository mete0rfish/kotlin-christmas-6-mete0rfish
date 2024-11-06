package store.product;

import java.util.Date;
import java.util.Objects;

import camp.nextstep.edu.missionutils.*;

public record Promotion(
        String name,
        Long buyCount,
        Long getCount,
        Date start_date,
        Date end_date
) {

    public Promotion findByName(String name) {
        if(Objects.equals(this.name, name)) {
            return this;
        }
        return null;
    }
}
