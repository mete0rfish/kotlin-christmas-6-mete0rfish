package store.product.view;

import store.product.Product;

import java.util.List;

public class OutputView {
    private final static String welcomeMessage = "안녕하세요. W편의점입니다.\n현재 보유하고 있는 상품입니다.\n";

    public void displayWelcomeAndList(List<Product> products) {
        System.out.println(welcomeMessage);
        products.forEach(product -> {
            System.out.println(product.toString());
        });
    }

}
