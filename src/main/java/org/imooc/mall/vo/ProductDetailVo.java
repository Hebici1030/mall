package org.imooc.mall.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ProductDetailVo {
    private Integer id;
    private Integer categoryId;
    private String name;
    private String subtitle;
    private String mainImage;
    private String detail;
    private String subImages;
    private Integer status;
    private BigDecimal price;
    private Integer stock;
    private Date  createTime;
    private Date  updateTime;

}
