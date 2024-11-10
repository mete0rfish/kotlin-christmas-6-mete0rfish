package store.product.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.product.dto.ProductFreeDTO;
import store.product.dto.ProductPaidDTO;
import store.product.dto.ProductRemainDTO;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {
    @DisplayName("멤버십 비활성화 시, 정상적으로 계산한다.")
    @Test
    void getTotalPaidPriceWithoutMembershipTest() {
        // given & when
        Store store = new Store();
        Cart cart = new Cart();
        cart.buyItem(store.getProducts().getFirst(), 10);

        ProductFreeDTO freeDTO = cart.getItems().getFirst().getFreeProduct();
        ProductPaidDTO paidDTO = cart.getItems().getFirst().getPaidProduct();
        ProductRemainDTO remainDTO = cart.getItems().getFirst().getRemainProduct();

        Payment payment = Payment.from(Arrays.asList(paidDTO), Arrays.asList(freeDTO), Arrays.asList(remainDTO));

        // then
        assertThat(payment.getTotalPrice()).isEqualTo(7000);
    }
    @DisplayName("멤버십 활성화 시, 정상적으로 계산한다.")
    @Test
    void getTotalPaidPriceWithMembershipTest() {
        // given & when
        Store store = new Store();
        Cart cart = new Cart();
        cart.buyItem(store.getProducts().getFirst(), 10);

        ProductFreeDTO freeDTO = cart.getItems().getFirst().getFreeProduct();
        ProductPaidDTO paidDTO = cart.getItems().getFirst().getPaidProduct();
        ProductRemainDTO remainDTO = cart.getItems().getFirst().getRemainProduct();

        Payment payment = Payment.from(Arrays.asList(paidDTO), Arrays.asList(freeDTO), Arrays.asList(remainDTO));
        payment.useMembership();

        // then
        assertThat(payment.getTotalPrice()).isEqualTo(6700);
    }

}