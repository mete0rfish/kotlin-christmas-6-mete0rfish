package store.product.view;

import store.product.Product;

import static camp.nextstep.edu.missionutils.Console.readLine;

public class InputView {
    private final static String buyMessage = "구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])";
    private final static String membershipMessage = "멤버십 할인을 받으시겠습니까? (Y/N)";
    private final static String notPromotionMessage = "현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)";
    private final static String lessPromotionMessage = "현재 %s은(는) %d개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)";

    public String inputBuy() {
        System.out.println(buyMessage);
        return readLine();
    }

    public String inputMembership() {
        System.out.println(membershipMessage);
        return readLine();
    }

    public String inputNotPromotion(String name, int count) {
        System.out.printf(notPromotionMessage, name, count);
        return readLine();
    }

    public String inputLessPromotion(String name, int count) {
        System.out.printf(lessPromotionMessage, name, count);
        return readLine();
    }
}
