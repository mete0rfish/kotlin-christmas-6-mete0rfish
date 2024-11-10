package store.util;

import java.util.HashMap;
import java.util.Map;

public class Parser {
    // [오렌지주스-1]
    public static Map<String, Integer> parseBuyInput(String input) {
        try{
            Map<String, Integer> productMap = new HashMap<>();
            String[] items = input.split(",");

            for (String item : items) {
                // "[상품명-수량]" 형식을 제거하고 상품명과 수량 추출
                item = item.trim().replaceAll("[\\[\\]]", ""); // 대괄호 제거
                String[] parts = item.split("-");
                String productName = parts[0];
                int quantity = Integer.parseInt(parts[1]);


                productMap.put(productName, quantity);
            }

            return productMap;
        } catch (NumberFormatException e){
            throw new IllegalArgumentException("[ERROR] 수량 형식이 올바르지 않습니다.");
        }
    }
}