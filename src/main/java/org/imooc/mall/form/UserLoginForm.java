package org.imooc.mall.form;

import lombok.Data;

import javax.validation.constraints.NotNull;
@Data
public class UserLoginForm {
    //    @NotBlank//判断字符串中空格
//    @NotEmpty//判断集合是否为空
    @NotNull(message = "用户名不能为空")
    private  String username;
    @NotNull(message = "密码不能为空")
    private String password;
}
