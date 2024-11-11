package store.product.view;

import store.product.domain.Cart;
import store.product.domain.Payment;
import store.product.domain.Product;
import store.product.domain.Store;
import store.product.dto.ProductFreeDTO;

import java.util.List;

public class OutputView {
    private final static String welcomeMessage = "안녕하세요. W편의점입니다.\n현재 보유하고 있는 상품입니다.\n";

    public void displayWelcomeAndList(Store store) {
        System.out.println(welcomeMessage);
        List<Product> products = store.getProducts();

        products.forEach(product -> {
            System.out.println(product.toString());
        });
    }

    public void displayPaymentInfo(Cart cart, Payment payment) {
        System.out.println("==============W 편의점================");
        System.out.printf("%-7s\t\t\t%-2s\t\t  %s%n", "상품명", "수량", "금액");
        cart.getItems()
                .forEach(cartItem -> {
                    System.out.printf("%-5s\t\t\t\t%-2s\t\t  %-5s%n", cartItem.getProductName(), cartItem.getTotalQuantity(), cartItem.getTotalPrice());
                });
        System.out.println("=============증\t\t정===============");
        cart.getItems()
                .forEach(cartItem -> {
                    ProductFreeDTO freeDTO = cartItem.getFreeProduct();
                    if(freeDTO.freeCount() != 0) {
                        System.out.printf("%-7s\t\t\t%-2s%n", freeDTO.name(), freeDTO.freeCount());
                    }
                });
        displayPrices(cart, payment);
    }

    private void displayPrices(Cart cart, Payment payment) {
        int totalQuantity = cart.getTotalQuantity();
        int totalPrice = cart.getTotalPrice();
        int totalPromotionPrice = payment.getFreePrice();
        int membershipDiscount = payment.getMembershipDiscount();
        int totalPaidPrice = payment.getTotalPrice();

        System.out.println("====================================");

        System.out.printf("%-7s\t\t\t%-2s\t\t  %,d%n", "총구매액", totalQuantity, totalPrice);
        System.out.printf("%-7s\t\t\t\t\t  -%,d%n", "행사할인", totalPromotionPrice);
        System.out.printf("%-7s\t\t\t\t\t  -%,d%n", "멤버십할인", membershipDiscount);
        System.out.printf("%-7s\t\t\t\t\t  %,d%n", "내실돈", totalPaidPrice);
    }
}
