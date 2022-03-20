package org.imooc.mall.controller;

import org.imooc.mall.MainApp;
import org.imooc.mall.consts.MallConst;
import org.imooc.mall.form.ShippingForm;
import org.imooc.mall.pojo.User;
import org.imooc.mall.service.ShippingService;
import org.imooc.mall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;


@RestController
public class ShippingController {
    @Autowired
    private ShippingService shippingService;
    @PostMapping("/shippings")
    public ResponseVo add(@Valid @RequestBody ShippingForm shippingForm,
                          HttpSession session){
        User user  = (User) session.getAttribute(MallConst.CURRENT_USER);
        return shippingService.add(user.getId(),shippingForm);
    }
    @DeleteMapping("/shippings/{shippingId}")
    public ResponseVo delete(@PathVariable Integer shippingId,
                          HttpSession session){
        User user  = (User) session.getAttribute(MallConst.CURRENT_USER);
        return shippingService.delete(user.getId(),shippingId);
    }
    @PutMapping("/shippings/{shippingId}")
    public ResponseVo delete(@Valid @RequestBody ShippingForm shippingForm,
                             @PathVariable Integer shippingId
            ,HttpSession session){
        User user  = (User) session.getAttribute(MallConst.CURRENT_USER);
        return shippingService.update(user.getId(),shippingId,shippingForm);
    }
    @GetMapping("/shippings")
    public ResponseVo list(@RequestParam(required = false,defaultValue = "1") Integer pagesize,
                           @RequestParam(required = false,defaultValue = "1") Integer pagenum,
                           @Valid@RequestBody ShippingForm shippingForm,
                           HttpSession session
                           ){
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return shippingService.list(user.getId(),pagesize,pagenum);
    }
}
