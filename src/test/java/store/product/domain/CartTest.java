package store.product.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CartTest {
    @DisplayName("Cart의 CartItem이 생성되었는지 확인한다.")
    @Test
    void createCartItemTest() {
        // given
        Store store = new Store();
        Cart cart = new Cart();

        // when
        cart.buyItem(store.getProducts().getFirst(), 10);

        //then
        assertThat(cart.getItems().getFirst().getFreeProduct().freeCount()).isEqualTo(3);
    }
}