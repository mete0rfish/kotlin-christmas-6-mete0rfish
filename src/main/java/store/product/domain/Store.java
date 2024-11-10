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
            Product product = findProductByName(response.name());
            if(product == null) {
                product = Product.of(response);
                products.add(product);
            }

            setPromotion(product, response.promotion(), response.quantity());
        });
    }

    private void setPromotion(Product product, String promotion, int quantity) {
        if(!promotion.equals("null")) {
            findPromotionByName(promotion).ifPresent((promo) -> {
                product.setPromotion(promo);
                product.setPromotionStock(quantity);
            });
            return;
        }

        product.setRegularStock(quantity);
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

    public Product findProductByName(String name) {
        return products.stream()
                .filter(product -> product.getName().equals(name))
                .findFirst().orElse(null);
    }
}
