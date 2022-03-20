package org.imooc.mall.service.Impl;

import org.imooc.mall.dao.UserMapper;
import org.imooc.mall.emnu.ResponseEmnu;
import org.imooc.mall.pojo.User;
import org.imooc.mall.service.UserService;
import org.imooc.mall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public ResponseVo register(User user) {
        int isExistName = userMapper.selectByName(user.getUsername());
        int isExistEmail = userMapper.selectByEmail(user.getEmail());
        if(isExistEmail==1||isExistName==1){
            return ResponseVo.error(ResponseEmnu.ERROE);
        }

        DigestUtils.md5Digest(user.getPassword().getBytes(StandardCharsets.UTF_8));
//写入数据库 因为通过user传入 可以通过逆向工程完成
        int selective = userMapper.insertSelective(user);
        if(selective==0){
            return ResponseVo.error(ResponseEmnu.ERROE);
        }
        return ResponseVo.success();
    }

    @Override
    public ResponseVo login(String username, String password) {
        User user = userMapper.selectByUserame(username);
        if(user==null){
            return  ResponseVo.error(ResponseEmnu.USERNAME_OR_PASSWORD_ERRPE);
        }
        if(!user.getPassword().equalsIgnoreCase(
                DigestUtils.md5DigestAsHex(
                        user.getPassword().getBytes(StandardCharsets.UTF_8)))){
            return  ResponseVo.error(ResponseEmnu.USERNAME_OR_PASSWORD_ERRPE);
        }
        //避免密码泄露
        user.setPassword("");
        return ResponseVo.success(user);
    }
}
