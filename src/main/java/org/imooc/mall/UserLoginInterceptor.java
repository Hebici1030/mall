package org.imooc.mall;


import lombok.extern.slf4j.Slf4j;
import org.imooc.mall.consts.MallConst;
import org.imooc.mall.exception.UserLoginException;
import org.imooc.mall.pojo.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@Slf4j
public class UserLoginInterceptor implements HandlerInterceptor {
    /**
     * true继续 false中断
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("PreHandler...");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        if(user==null){
            //讲错误false转化为ResponseVo 难点：函数类型为boolean 不能直接转换
            //解决办法：throw Exception Spring内置的错误处理
//            log.info("（拦截器）用户未登录");
//            return false;
            throw new UserLoginException();
        }
        return true;
    }
}
