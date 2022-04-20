package iee.yh.common.to;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author yanghan
 * @date 2022/4/16
 */
@Data
public class SkuReductionTo {
    private Long skuId;
    private int fullCount;
    private BigDecimal discount;
    private int countStatus;
    private BigDecimal fullPrics;
    private BigDecimal reducePrice;
    private int priceStatues;
    private List<MemberPrice> memberPrice;
}
