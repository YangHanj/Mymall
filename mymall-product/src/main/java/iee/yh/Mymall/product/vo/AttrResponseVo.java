package iee.yh.Mymall.product.vo;

import lombok.Data;

/**
 * @author yanghan
 * @date 2022/4/10
 */
@Data
public class AttrResponseVo extends AttrVo {
    /**
     * "catelogname":分类名称
     * "groupname":分组名称
     */
    private String catelogName;
    private String groupName;
    private Long[] catelogPath;
}
