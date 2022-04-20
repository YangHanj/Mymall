package iee.yh.Mymall.ware.vo;

import lombok.Data;

import java.util.List;

/**
 * @author yanghan
 * @date 2022/4/18
 */
@Data
public class MergeVo {
    private Long purchaseId;
    private List<Long> items;
}
