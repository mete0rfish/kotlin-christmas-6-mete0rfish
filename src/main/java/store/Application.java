package store;

import store.product.controller.ProductController;
import store.product.view.InputView;
import store.product.view.OutputView;

public class Application {
    public static void main(String[] args) {
        // TODO: 프로그램 구현
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();

        ProductController controller = new ProductController(inputView, outputView);
        controller.run();
    }
}
