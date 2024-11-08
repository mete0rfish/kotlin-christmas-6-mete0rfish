package store.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.product.domain.Store;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class StoreTest {
    @DisplayName("Product 객체를 정상적으로 생성한다.")
    @Test
    void testCreateProduct() {
        //given & when
        Store store = new Store();

        //then
        //assertThat(store.getProducts().size()).isEqualTo(16);
        System.out.println(store.getProducts().get(1).toString());
    }
}