package store.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.product.dto.ProductReadResponse;
import store.product.dto.PromotionReadResponse;
import store.util.MdReader;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PromotionTest {

    @DisplayName("MD 파일을 읽어 PromotionResponse 리스트로 반환한다.")
    @Test
    void createPromotionTest() {
        // given
        // when
        List<PromotionReadResponse> responseList = MdReader.readPromotion();
        System.out.println(responseList.get(1).toString());
        // then
        assertThat(responseList.size()).isEqualTo(3);
    }
}