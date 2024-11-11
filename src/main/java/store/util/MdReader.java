package store.util;

import store.product.dto.ProductReadResponse;
import store.product.dto.PromotionReadResponse;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MdReader {
    private final static String productMdPath = "src/main/resources/products.md";
    private final static String promotionMdPath = "src/main/resources/promotions.md";

    public static List<ProductReadResponse> readProduct() {
        List<ProductReadResponse> productList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(productMdPath))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                String name = fields[0];
                int price = Integer.parseInt(fields[1]);
                int quantity = Integer.parseInt(fields[2]);
                String promotion = fields.length > 3 ? fields[3] : null;
                ProductReadResponse product = new ProductReadResponse(name, price, quantity, promotion);
                productList.add(product);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return productList;
    }

    public static List<PromotionReadResponse> readPromotion() {
        List<PromotionReadResponse> promotionList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(promotionMdPath))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                String name = fields[0];
                int buy = Integer.parseInt(fields[1]);
                int get = Integer.parseInt(fields[2]);
                String start_date = fields[3];
                String end_date = fields[4];
                PromotionReadResponse response = new PromotionReadResponse(name, buy, get, start_date, end_date);
                promotionList.add(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return promotionList;
    }
}
