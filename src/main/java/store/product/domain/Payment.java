package store.product.domain;

import store.product.dto.ProductFreeDTO;
import store.product.dto.ProductPaidDTO;
import store.product.dto.ProductRemainDTO;

import java.util.List;

/*
 - 맴버십 할인 액 = min(8000, (결제 금액 * 0.3))
 - 총 결제 액 = 결제 금액 - 멤버십 할인 액
 */
public class Payment {
    private final static int MAX_MEMBERSHIP_DISCOUNT = 8000;

    private int paidPrice;
    private int freePrice;
    private int remainPrice;
    private boolean membershipStatus = false;

    public Payment(int paidPrice, int freePrice, int remainPrice) {
        this.remainPrice = remainPrice;
        this.freePrice = freePrice;
        this.paidPrice = paidPrice;
    }

    public static Payment from(List<ProductPaidDTO> paidDTOs, List<ProductFreeDTO> freeDTOs, List<ProductRemainDTO> remainDTOs) {
        return new Payment(
                paidDTOs.stream().mapToInt(ProductPaidDTO::price).sum(),
                freeDTOs.stream().mapToInt(ProductFreeDTO::price).sum(),
                remainDTOs.stream().mapToInt(ProductRemainDTO::price).sum()
        );
    }

    public void useMembership() {
        this.membershipStatus = true;
    }

    public int getTotalPrice() {
        if(membershipStatus) {
            return paidPrice + remainPrice - getMembershipDiscount();
        }
        return paidPrice + remainPrice;
    }

    public int getPaidPrice() {
        return paidPrice;
    }

    public int getFreePrice() {
        return freePrice;
    }

    public int getMembershipDiscount() {
        if(membershipStatus) {
            return Math.min(MAX_MEMBERSHIP_DISCOUNT, (int)(remainPrice * 0.3));
        }
        return 0;
    }

}
