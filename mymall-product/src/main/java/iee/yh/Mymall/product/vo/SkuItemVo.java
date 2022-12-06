package iee.yh.Mymall.product.vo;

import iee.yh.Mymall.product.entity.SkuImagesEntity;
import iee.yh.Mymall.product.entity.SkuInfoEntity;
import iee.yh.Mymall.product.entity.SpuInfoDescEntity;

import java.util.List;

/**
 * @author yanghan
 * @date 2022/11/26
 */
public class SkuItemVo {

    private SkuInfoEntity info;
    private List<SkuImagesEntity> images;
    private List<skuItemSaleAttrVo> sKuItemSaleAttrVo;
    private SpuInfoDescEntity desp;
    private List<spuItemAttrGroupVo> groupAttrs;

    public SkuInfoEntity getInfo() {
        return info;
    }

    public void setInfo(SkuInfoEntity info) {
        this.info = info;
    }

    public List<SkuImagesEntity> getImages() {
        return images;
    }

    public void setImages(List<SkuImagesEntity> images) {
        this.images = images;
    }

    public List<skuItemSaleAttrVo> getsKuItemSaleAttrVo() {
        return sKuItemSaleAttrVo;
    }

    public void setsKuItemSaleAttrVo(List<skuItemSaleAttrVo> sKuItemSaleAttrVo) {
        this.sKuItemSaleAttrVo = sKuItemSaleAttrVo;
    }

    public SpuInfoDescEntity getDesp() {
        return desp;
    }

    public void setDesp(SpuInfoDescEntity desp) {
        this.desp = desp;
    }

    public List<spuItemAttrGroupVo> getGroupAttrs() {
        return groupAttrs;
    }

    public void setGroupAttrs(List<spuItemAttrGroupVo> groupAttrs) {
        this.groupAttrs = groupAttrs;
    }

    public static class skuItemSaleAttrVo{
        private Long attrId;
        private String attrName;
        private List<String> attrValues;

        public Long getAttrId() {
            return attrId;
        }

        public void setAttrId(Long attrId) {
            this.attrId = attrId;
        }

        public String getAttrName() {
            return attrName;
        }

        public void setAttrName(String attrName) {
            this.attrName = attrName;
        }

        public List<String> getAttrValues() {
            return attrValues;
        }

        public void setAttrValues(List<String> attrValues) {
            this.attrValues = attrValues;
        }
    }
    public static class spuItemAttrGroupVo{
        private String groupName;
        private List<spuBaseAttrVo> attrs;

        public String getGroupName() {
            return groupName;
        }

        public void setGroupName(String groupName) {
            this.groupName = groupName;
        }

        public List<spuBaseAttrVo> getAttrs() {
            return attrs;
        }

        public void setAttrs(List<spuBaseAttrVo> attrs) {
            this.attrs = attrs;
        }
    }
    public static class spuBaseAttrVo{
        private Long attrId;
        private String attrValue;

        public Long getAttrId() {
            return attrId;
        }

        public void setAttrId(Long attrId) {
            this.attrId = attrId;
        }

        public String getAttrValue() {
            return attrValue;
        }

        public void setAttrValue(String attrValue) {
            this.attrValue = attrValue;
        }
    }
}
