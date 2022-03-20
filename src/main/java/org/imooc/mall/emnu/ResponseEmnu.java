package org.imooc.mall.emnu;

import lombok.Getter;

/*
将状态打包为枚举类
 */
@Getter
public enum ResponseEmnu {
    ERROE(-1,"服务器端错误"),
    SUCCES(0,"成功"),
    PASSWORD_ERROR(1,"密码错误"),
    USER_EXIST(2,"用户已存在"),
    NEED_LOGIN(10,"用户未登录"),
    USERNAME_OR_PASSWORD_ERRPE(11,"用户密码或用户名错误"),
    PARAM_ERROR(3,"参数错误"),
    PRODUCT_OFF_SALE_OR_DELETE(12,"商品下架或删除"),
    PRODUCT_NOT_EXIST(13,"商品不存在"),
    PRODUCT_STOCK_ERROE(14,"商品库存不正确"),
    CART_PRODUCT_NOT_EXIST(15,"购物车不存在此商品"),
    DELETE_SHIPPING_FAIL(16,"不存在地址"),
    SHIPPING_NOT_EXIST(17,"查询收货地址失败"),
    CART_SELECTED_IS_EMPTY(18,"购物车未选中商品" );
    Integer code;
    String stuts;
    ResponseEmnu(Integer code, String stuts) {
        this.code = code;
        this.stuts = stuts;
    }
}
