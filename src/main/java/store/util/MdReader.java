package store.util;

import store.product.Product;
import store.product.dto.ProductReadResponse;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MdReader {
    public List<ProductReadResponse> read(String filePath) {
        List<ProductReadResponse> productList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // 헤드 삭제

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
}
