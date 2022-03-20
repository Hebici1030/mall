package org.imooc.mall.exception;

import org.imooc.mall.emnu.ResponseEmnu;
import org.imooc.mall.vo.ResponseVo;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/*

 */
@ControllerAdvice
public class RuntimeExceptionHandler {
    @ExceptionHandler(java.lang.RuntimeException.class)
    @ResponseBody
    public ResponseVo handler(RuntimeException e){
        return ResponseVo.error(ResponseEmnu.ERROE,e.getMessage());
    }
    @ExceptionHandler(UserLoginException.class)
    @ResponseBody
    public ResponseVo userLoginHandler(UserLoginException e){
        return ResponseVo.error(ResponseEmnu.NEED_LOGIN);
    }
}
