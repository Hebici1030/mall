package org.imooc.mall.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CartUpdateForm {
    @NotNull
    private Integer quantity;
    @NotNull
    private Boolean selected;
}
