package org.imooc.mall.controller;

import lombok.extern.slf4j.Slf4j;
import org.imooc.mall.consts.MallConst;
import org.imooc.mall.emnu.ResponseEmnu;
import org.imooc.mall.form.UserLoginForm;
import org.imooc.mall.form.UserRegisterForm;
import org.imooc.mall.pojo.User;
import org.imooc.mall.service.UserService;
import org.imooc.mall.vo.ResponseVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.xml.ws.http.HTTPBinding;

//RestController  = @RequesBody @Controller
@RestController
@Slf4j
//声明:如果不想每次都写private  final Logger logger = LoggerFactory.getLogger(当前类名.class); 可以用注解@Slf4j;
public class UserController {
//     @PostMapping("/register")
//    public ResponseVo register(@RequestParam String name){
//
//         return ResponseVo.success("成功");
//     }
//@PostMapping("/register")
//public ResponseVo register(@RequestParam String name){
//
//    return ResponseVo.success("成功");
//}
//@PostMapping("/register")
//public ResponseVo register(@RequestParam UserForm userForm, BindingResult bindingResult){
//    if(bindingResult.hasErrors()){
//        log.error("提交注册参数有误,{}{}",
//                bindingResult.getFieldError().getField(),
//                bindingResult.getFieldError().getArguments());
//        //return ResponseVo.error(ResponseEmnu.ERROE); 通过enum返回不为动态信息 因此可以加入一个BingResult参数方法
//        return ResponseVo.error(ResponseEmnu.ERROE,bindingResult);
//    }
//    return ResponseVo.success("成功");
//}@Autowired
//    UserService userService;
    @Autowired
    private UserService userService;
@PostMapping("/user/register")
//@Valid
//用于验证注解是否符合要求，直接加在变量user之前，在变量中添加验证信息的要求，当不符合要求时就会在方法中返回message 的错误提示信息。
public ResponseVo register(@Valid @RequestParam UserRegisterForm userForm,
                           BindingResult bindingResult){
    if(bindingResult.hasErrors()){
        log.error("提交注册参数有误,{}{}",
                bindingResult.getFieldError().getField(),
                bindingResult.getFieldError().getArguments());
        //return ResponseVo.error(ResponseEmnu.ERROE); 通过enum返回不为动态信息 因此可以加入一个BingResult参数方法
        return ResponseVo.error(ResponseEmnu.PARAM_ERROR,bindingResult);
    }
    User user = new User();
    //copyproperties这个功能,相同的属性都会被替换,不管是否有值
    BeanUtils.copyProperties(userForm,user);
    return userService.register(user);
}

@PostMapping("/user/login")
//RequestBody将前端参数通过json对应到后端
//每次只暴漏了用户名和密码
    public ResponseVo<User> login(@Valid @RequestBody UserLoginForm userLoginForm,
                                  BindingResult result,
                                  HttpServletRequest httpServletRequest){
    if(result.hasErrors()){
        return ResponseVo.error(ResponseEmnu.ERROE,result);
    }
    ResponseVo<User> login = userService.login(userLoginForm.getUsername(), userLoginForm.getUsername());
    //设置session()
    HttpSession session = httpServletRequest.getSession(true);
    //通过泛型将ResponseVo 的getData()得到的为User类型
    //将setAttribute类型的字符串进行包装为类 consts
    session.setAttribute(MallConst.CURRENT_USER,login.getData());
    return login;
}
@GetMapping("/user")
    public  ResponseVo<User> userInfo(HttpSession httpSession){
    //如何在不显式传参情况下session传入了user类型的对象？ 怎确保不同服务端会使用相同的session
    User user =(User) httpSession.getAttribute(MallConst.CURRENT_USER);
    if(user==null){
        return ResponseVo.error(ResponseEmnu.NEED_LOGIN);
    }
    return ResponseVo.success(user);
}
//TODO  判断登陆状态 拦截器
@PostMapping("/user/logout")
/**
 * (@link TomcatServletWEbServerFactory) getSessionTimeoutInMinutes
 */
    public ResponseVo loggout(HttpSession httpSession){
    log.info("/user/logout session={}",httpSession.getId());
    User user = (User)httpSession.getAttribute(MallConst.CURRENT_USER);
    if(user==null){
        return ResponseVo.error(ResponseEmnu.NEED_LOGIN);
    }
    httpSession.removeAttribute(MallConst.CURRENT_USER);
    return ResponseVo.success(user);
}
}
