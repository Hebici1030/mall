package org.imooc.mall.controller;

import org.imooc.mall.consts.MallConst;
import org.imooc.mall.form.CartAddForm;
import org.imooc.mall.form.CartUpdateForm;
import org.imooc.mall.pojo.User;
import org.imooc.mall.service.CartService;
import org.imooc.mall.vo.CartVo;
import org.imooc.mall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
public class CartController {
    @Autowired
    private CartService cartService;
    //如何为一个账户连接购物车
    @GetMapping("/carts")
    public ResponseVo<CartVo> list(@Valid @RequestBody CartAddForm cartAddForm,
                                  HttpSession httpSession){
        User user = (User) httpSession.getAttribute(MallConst.CURRENT_USER);
        return cartService.add(user.getId(),cartAddForm);
    }
    @PostMapping("/carts")
    public ResponseVo<CartVo> add(@Valid @RequestBody CartAddForm cartAddForm,

                                  HttpSession httpSession){
        User user = (User) httpSession.getAttribute(MallConst.CURRENT_USER);
        return cartService.add(user.getId(),cartAddForm);
    }
    @PutMapping("/carts/{productId}")
    public ResponseVo<CartVo> update(@PathVariable Integer productId,
                                     @Valid @RequestBody CartUpdateForm cartUpdateForm,
                                  HttpSession httpSession){
        User user = (User) httpSession.getAttribute(MallConst.CURRENT_USER);
        return cartService.Update(user.getId(),productId,cartUpdateForm);
    }
    @DeleteMapping("/carts/{product}")
    public ResponseVo<CartVo> delete(@PathVariable Integer productId,
                                     HttpSession httpSession){
        User user = (User) httpSession.getAttribute(MallConst.CURRENT_USER);
        return cartService.delete(user.getId(),productId);
    }
    @PutMapping("/carts/selectAll")
    public ResponseVo<CartVo> selectAll(HttpSession httpSession){
        User user = (User) httpSession.getAttribute(MallConst.CURRENT_USER);
        return cartService.selectAll(user.getId());
    }
    @PutMapping("/carts/unselectAll")
    public ResponseVo<CartVo> unselectAll(HttpSession httpSession){
        User user = (User) httpSession.getAttribute(MallConst.CURRENT_USER);
        return cartService.unselectAll(user.getId());
    }
    @GetMapping("/carts/products/sum")
    public ResponseVo<Integer> summ(HttpSession httpSession){
        User user = (User) httpSession.getAttribute(MallConst.CURRENT_USER);
        return cartService.summ(user.getId());
    }
}
