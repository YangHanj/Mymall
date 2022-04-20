/**
  * Copyright 2022 json.cn 
  */
package iee.yh.common.to;

import java.math.BigDecimal;

/**
 * Auto-generated: 2022-04-13 9:6:4
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
public class MemberPrice {

    private Long id;
    private String name;
    private BigDecimal price;
    public void setId(Long id) {
         this.id = id;
     }
     public Long getId() {
         return id;
     }

    public void setName(String name) {
         this.name = name;
     }
     public String getName() {
         return name;
     }

    public void setPrice(BigDecimal price) {
         this.price = price;
     }
     public BigDecimal getPrice() {
         return price;
     }

}