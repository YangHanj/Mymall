package iee.yh.Mymall.ware.vo;

public class SkuHasstockVo {
    private Long skuId;
    private Boolean hasstock;

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Boolean getHasstock() {
        return hasstock;
    }

    public void setHasstock(Boolean hasstock) {
        this.hasstock = hasstock;
    }

    public SkuHasstockVo() {
    }

    public SkuHasstockVo(Long skuId, Boolean hasstock) {
        this.skuId = skuId;
        this.hasstock = hasstock;
    }
}
