package store.product.domain;

import store.product.dto.ProductReadResponse;
import store.product.dto.PromotionReadResponse;
import store.util.MdReader;

import java.util.*;

public class Store {
    private final List<Product> products = new ArrayList<>();
    private final List<Promotion> promotions = new ArrayList<>();

    public Store() {
        createPromotions();
        createProducts();
    }

    public void createProducts() {
        List<ProductReadResponse> responses = MdReader.readProduct();
        responses.forEach(response -> {
            if(response.promotion().equals("null")) {
                findProductByName(response.name()).ifPresent(product -> product.setRegularStock(response.quantity()));
            }
            if(!response.promotion().equals("null")) {
                Product product = Product.of(response, findPromotionByName(response.promotion())
                        .orElseThrow(() -> new IllegalArgumentException("프로모션을 찾을 수 없습니다: " + response.promotion())));
                products.add(product);
            }
        });
    }

    public void createPromotions() {
        List<PromotionReadResponse> responses = MdReader.readPromotion();
        responses.forEach(promotion -> {
            promotions.add(Promotion.of(promotion));
        });
    }

    public List<Product> getProducts() {
        return products;
    }

    public List<Promotion> getPromotions() {
        return promotions;
    }

    public Optional<Promotion> findPromotionByName(String name) {
        return promotions.stream()
                .filter(promotion -> promotion.name().equals(name))
                .findAny();
    }

    public Optional<Product> findProductByName(String name) {
        return products.stream()
                .filter(product -> product.getName().equals(name))
                .findAny();
    }
}
