package iee.yh.Mymall.product.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import iee.yh.Mymall.product.entity.AttrEntity;
import lombok.Data;

import java.util.List;

/**
 * @author yanghan
 * @date 2022/4/12
 */
@Data
public class AttrGroupWithAttrsVo {
    private static final long serialVersionUID = 1L;

    /**
     * 分组id
     */
    private Long attrGroupId;
    /**
     * 组名
     */
    private String attrGroupName;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 描述
     */
    private String descript;
    /**
     * 组图标
     */
    private String icon;
    /**
     * 所属分类id
     */
    private Long catelogId;

    private List<AttrEntity> attrs;
}
