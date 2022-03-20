package org.imooc.mall.service;

import org.imooc.mall.pojo.User;
import org.imooc.mall.vo.ResponseVo;

public interface UserService {
    /*
    注册
     */
    public ResponseVo register(User user);

    /*
    登录
     */
    public ResponseVo login(String username,String password);
}
