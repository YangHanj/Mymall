package iee.yh.common.to;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author yanghan
 * @date 2022/4/16
 */
@Data
public class SpuBoundTo {
    private Long spuId;
    private BigDecimal buyBounds;
    private BigDecimal growBounds;
}
