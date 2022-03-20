package org.imooc.mall.pojo;

import lombok.Data;

@Data
public class Cart {
    //商品ID
    private Integer productId;
    //数量
    private Integer quantity;
    //是否选择呢
    private Boolean selected;

    public Cart() {
    }

    public Cart(Integer productId, Integer quantity, Boolean selected) {
        this.productId = productId;
        this.quantity = quantity;
        this.selected = selected;
    }
}
