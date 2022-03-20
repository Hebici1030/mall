package org.imooc.mall.service;

import org.imooc.mall.form.CartAddForm;
import org.imooc.mall.form.CartUpdateForm;
import org.imooc.mall.pojo.Cart;
import org.imooc.mall.vo.CartVo;
import org.imooc.mall.vo.ResponseVo;

import java.util.List;

public interface CartService {

    ResponseVo<CartVo> add(Integer uid,CartAddForm cartAddForm);

    ResponseVo<CartVo> list(Integer uid);

    ResponseVo<CartVo> Update(Integer uid, Integer productId, CartUpdateForm cartUpdateForm);

    ResponseVo<CartVo> delete(Integer uid,Integer productId);

    ResponseVo<CartVo> selectAll(Integer uid);

    ResponseVo<CartVo> unselectAll(Integer uid);

    ResponseVo<Integer> summ(Integer uid);

    List<Cart> listForCart(Integer uid);
}
