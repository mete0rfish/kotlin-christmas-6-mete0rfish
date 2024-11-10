package store.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.product.domain.Product;
import store.product.domain.Store;
import store.product.dto.ProductReadResponse;
import store.product.view.InputView;
import store.util.MdReader;
import store.util.Parser;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

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

    @DisplayName("공짜 개수와 돈주고 사야하는 개수를 구한다.")
    @Test
    void calculateCountTest() {
        // given
        Store store = new Store();

        // when
        Product product = store.getProducts().get(0);
        int[] counts = product.calculateFreeAndPaidStock(7);

        // then
        System.out.println("paid: " + counts[0] + "\nfree: " + counts[1] + "\nremain: " + counts[2]);
    }

    @DisplayName("잘못된 상품명 입력에 대해 예외가 발생한다.")
    @Test
    void inputNameExceptionTest() {
        // given
        Store store = new Store();

        // when
        String input = "[오렌지주-1]";
        Map<String, Integer> nameAndQuantity = Parser.parseBuyInput(input);
        String name = nameAndQuantity.entrySet().iterator().next().getKey();

        // then
        assertThat(store.findProductByName(name)).isEqualTo(null);
    }

    @DisplayName("잘못된 개수 입력에 대해 예외가 발생한다.")
    @Test
    void inputQuantityExceptionTest() {
        // given
        Store store = new Store();

        // when
        String input = "[오렌지주스-d]";


        // then
        assertThatThrownBy(() -> {
            Parser.parseBuyInput(input);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
    }
}