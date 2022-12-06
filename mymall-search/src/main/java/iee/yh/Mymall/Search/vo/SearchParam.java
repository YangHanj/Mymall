package iee.yh.Mymall.Search.vo;

import java.util.List;

/**
 * 封装页面可能传递过来的查询条件
 * @author yanghan
 * @date 2022/11/26
 */
public class SearchParam {

    private String keyword; // 全文匹配关键字
    private Long catalog3Id; // 三级分类id
    private String sort;   // 排序
    private Integer hasStock;// 是否有货
    private String skuPrice;// 价格区间
    private List<Long> brandId; // 品牌
    private List<String> attrs; // 属性
    private Integer pageNum; // 页码

    public SearchParam() {
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Long getCatalog3Id() {
        return catalog3Id;
    }

    public void setCatalog3Id(Long catalog3Id) {
        this.catalog3Id = catalog3Id;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public Integer getHasStock() {
        return hasStock;
    }

    public void setHasStock(Integer hasStock) {
        this.hasStock = hasStock;
    }

    public String getSkuPrice() {
        return skuPrice;
    }

    public void setSkuPrice(String skuPrice) {
        this.skuPrice = skuPrice;
    }

    public List<Long> getBrandId() {
        return brandId;
    }

    public void setBrandId(List<Long> brandId) {
        this.brandId = brandId;
    }

    public List<String> getAttrs() {
        return attrs;
    }

    public void setAttrs(List<String> attrs) {
        this.attrs = attrs;
    }
}
