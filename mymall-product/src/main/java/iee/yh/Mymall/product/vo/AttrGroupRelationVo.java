package iee.yh.Mymall.product.vo;

import lombok.Data;

/**
 * @author yanghan
 * @date 2022/4/10
 */
@Data
public class AttrGroupRelationVo {
    public void setAttrGroupId(Long attrGroupId) {
        this.attrGroupId = attrGroupId;
    }

    public void setAttrId(Long attrId) {
        this.attrId = attrId;
    }

    public AttrGroupRelationVo(){

    }
    private Long attrId;
    private Long attrGroupId;
}
