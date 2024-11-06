package store.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.product.dto.ProductReadResponse;
import store.util.MdReader;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProductTest {
    @DisplayName("MD 파일을 읽어 ProductResponse 리스트로 반환한다.")
    @Test
    void createProductTest() {
        // given
        MdReader mdReader = new MdReader();
        // when
        List<ProductReadResponse> responseList = mdReader.read("src/main/resources/products.md");
        // then
        assertThat(responseList.size()).isEqualTo(16);
    }
}