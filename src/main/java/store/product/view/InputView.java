package store.product.view;

import static camp.nextstep.edu.missionutils.Console.readLine;

public class InputView {
    private final static String buyMessage = "\n구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])";
    private final static String membershipMessage = "\n멤버십 할인을 받으시겠습니까? (Y/N)";
    private final static String notPromotionMessage = "\n현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)\n";
    private final static String lessPromotionMessage = "\n현재 %s은(는) %d개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)\n";
    private final static String moreBuyMessage = "\n감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)";

    public String inputBuy() {
        System.out.println(buyMessage);
        return readLine();
    }

    public String inputMembership() {
        System.out.println(membershipMessage);
        return readLine();
    }

    public static String inputNotPromotion(String name, int count) {
        System.out.printf(notPromotionMessage, name, count);
        String input = readLine();
        if (input.equals("Y") || input.equals("N")) {
            return input;
        }
        return inputNotPromotion(name, count);
    }

    public static String inputLessPromotion(String name, int count) {
        System.out.printf(lessPromotionMessage, name, count);
        String input = readLine();
        if (input.equals("Y") || input.equals("N")) {
            return input;
        }
        return inputLessPromotion(name, count);
    }

    public String inputMoreBuy() {
        System.out.println(moreBuyMessage);
        String input = readLine();
        if (input.equals("Y") || input.equals("N")) {
            return input;
        }
        return inputMoreBuy();
    }
}
