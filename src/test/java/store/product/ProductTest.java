package store.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.product.dto.ProductReadResponse;
import store.util.MdReader;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTest {
    @DisplayName("MD 파일을 읽어 ProductResponse 리스트로 반환한다.")
    @Test
    void createProductTest() {
        // given
        // when
        List<ProductReadResponse> responseList = MdReader.readProduct();
        System.out.println(responseList.getFirst().toString());
        // then
        assertThat(responseList.size()).isEqualTo(16);
    }
}