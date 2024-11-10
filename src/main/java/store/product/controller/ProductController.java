package store.product.controller;

import store.product.domain.Cart;
import store.product.domain.Payment;
import store.product.domain.Product;
import store.product.domain.Store;
import store.product.dto.ProductPaidDTO;
import store.product.dto.ProductReadResponse;
import store.product.view.InputView;
import store.product.view.OutputView;
import store.util.MdReader;
import store.util.Parser;

import java.util.*;

public class ProductController {
    private InputView inputView;
    private OutputView outputView;

    public ProductController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        Store store = new Store();

        while(true) {

            Cart cart = new Cart();

            Map<String, Integer> nameAndQuantity = inputBuy(store);

            buyProducts(store, nameAndQuantity, cart);
            Payment payment = Payment.from(cart.getCartItemPaidDTOs(), cart.getCartItemFreeDTOs(), cart.getCartItemRemainDTOs());
            setMembership(payment);

            outputView.displayPaymentInfo(cart, payment);
            String input = inputView.inputMoreBuy();
            if(input.equals("N")) {
                break;
            }
        }
    }

    private Map<String, Integer> inputBuy(Store store) {
        try {
            outputView.displayWelcomeAndList(store);
            String input = inputView.inputBuy();
            return Parser.parseBuyInput(input);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return inputBuy(store);
        }
    }

    private void buyProducts(Store store, Map<String, Integer> nameAndQuantity, Cart cart) {
        nameAndQuantity.keySet().forEach(name -> {
            Product product = store.findProductByName(name);
            int quantity = nameAndQuantity.get(name);
            if (product != null) {
                cart.buyItem(product, quantity);
            }
        });
    }

    private void setMembership(Payment payment) {
        if (inputMembership()) {
            payment.useMembership();
        }
    }

    private boolean inputMembership() {
        String input = inputView.inputMembership();
        if (input.equals("Y") || input.equals("N")) {
            return input.equals("Y");
        }
        System.out.println("[ERROR] 지원하지 않는 형식입니다.");
        return inputMembership();
    }

}
