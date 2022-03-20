package org.imooc.mall.emnu;

import lombok.Getter;

@Getter
public enum RoleEnum {
    CUSTOME(0),
    ADMIN(1);
    Integer i;

    RoleEnum(Integer i) {
        this.i = i;
    }
}
