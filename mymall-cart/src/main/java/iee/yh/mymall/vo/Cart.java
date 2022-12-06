package iee.yh.mymall.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * 整个购物车
 * @author yanghan
 * @date 2022/12/4
 */
public class Cart {

    private Integer countNum; // 商品数量

    private List<CartItem> items; // 每一件商品信息

    private Integer countType; // 商品类型

    private BigDecimal totalAmount; // 商品总价

    private BigDecimal reduce = new BigDecimal(0); // 减免价格

    public Integer getCountNum() {
        int countNum = 0;
        if (items != null && items.size() > 0){
            for (CartItem item : items) {
                countNum += item.getCount();
            }
        }
        return countNum;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public Integer getCountType() {
        int count = 0;
        if (items != null && items.size() > 0){
            for (CartItem item : items) {
                count += 1;
            }
        }
        return count;
    }

    public BigDecimal getTotalAmount() {
        BigDecimal amount = new BigDecimal(0);
        if (items != null && items.size() > 0){
            for (CartItem item : items) {
                BigDecimal totalPrice = item.getTotalPrice();
                amount = amount.add(totalPrice);
            }
        }
        amount = amount.divide(reduce);
        return amount;
    }

    public BigDecimal getReduce() {
        return reduce;
    }

    public void setReduce(BigDecimal reduce) {
        this.reduce = reduce;
    }
}
