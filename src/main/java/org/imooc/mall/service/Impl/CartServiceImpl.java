package org.imooc.mall.service.Impl;

import com.google.gson.Gson;
import io.netty.util.internal.StringUtil;
import org.imooc.mall.dao.ProductMapper;
import org.imooc.mall.emnu.ProductStatusEnum;
import org.imooc.mall.emnu.ResponseEmnu;
import org.imooc.mall.form.CartAddForm;
import org.imooc.mall.form.CartUpdateForm;
import org.imooc.mall.pojo.Cart;
import org.imooc.mall.pojo.Product;
import org.imooc.mall.service.CartService;
import org.imooc.mall.vo.CartProdutVo;
import org.imooc.mall.vo.CartVo;
import org.imooc.mall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CartServiceImpl implements CartService {
    private final static Integer CART_QUANTITY = 1;
    private final static String CART_REDIS_KEY_TEMPLATE = "char_%d";
    @Autowired
    private ProductMapper productMapper;

    public CartServiceImpl(ProductMapper productMapper, StringRedisTemplate redisTemplate, Gson gson) {
        this.productMapper = productMapper;
        this.redisTemplate = redisTemplate;
        this.gson = gson;
    }

    @Autowired
    /*
     provides a dedicated class that minimizes configuration of its more generic template especially in terms of serializers.
     */
    private StringRedisTemplate redisTemplate;
    @Autowired
    private Gson gson;
    @Override
    public ResponseVo<CartVo> add(Integer uid,CartAddForm cartAddForm) {
        Product product = productMapper.selectByPrimaryKey(cartAddForm.getProductId());
        //判断商品是否存在
        if(product==null){
            return ResponseVo.error(ResponseEmnu.PRODUCT_NOT_EXIST);
        }
        //是否在售
        if(!product.getStatus().equals(ProductStatusEnum.ON_SALE.getCode())){
            return ResponseVo.error(ResponseEmnu.PRODUCT_OFF_SALE_OR_DELETE);
        }
        //是否有库存
        if(product.getStock()<=0){
            return ResponseVo.error(ResponseEmnu.PRODUCT_STOCK_ERROE);
        }
        //写入redis 增加缓存提高效率
//        redisTemplate.opsForValue().set(String.format(CART_REDIS_KEY_TEMPLATE,uid),gson.toJson(new Cart(product.getId(),CART_QUANTITY,cartAddForm.getSelected())));
        //此实现方案只能不断向redis缓存中加入不能重复的 商品ID->Cart的哈希映射表
//
//        HashOperations<String, String, String> ops = redisTemplate.opsForHash();
//        ops.put(String.format(CART_REDIS_KEY_TEMPLATE,uid),
//                product.getId().toString()
//                ,gson.toJson(new Cart(product.getId(),CART_QUANTITY,cartAddForm.getSelected())));
        Cart cart;
        HashOperations<String, String, String> ops = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE,uid);
        //redis查询购物车关键字下面的productId的序列
        String value = ops.get(redisKey, String.valueOf(product.getId()));
        if(StringUtil.isNullOrEmpty(value)){
            //购物车未加入商品
            cart = new Cart(product.getId(),CART_QUANTITY,cartAddForm.getSelected());
        }else{
            //已经加入商品 数量加一
            //从gson序列读出class
            cart = gson.fromJson(value,Cart.class);
            cart.setQuantity(cart.getQuantity()+CART_QUANTITY);
        }
        //hash set值存在时候会自动覆盖
        ops.put(redisKey,
                product.getId().toString(),
                gson.toJson(cart));
        return list(uid);
    }
    //购物车列表功能实现
    @Override
    public ResponseVo<CartVo> list(Integer uid) {
        String key = String.format(CART_REDIS_KEY_TEMPLATE, uid);
        HashOperations<String, String, String> ops = redisTemplate.opsForHash();
        Map<String, String> entries = ops.entries(key);

        CartVo cartVo = new CartVo();
        List<CartProdutVo> cartProdutVoList = new ArrayList<>();
        boolean selectAll = true;
        Integer cartTotalQuantity = 0;
        BigDecimal cartTotalPrice = BigDecimal.ZERO;
        for (Map.Entry<String, String> entry : entries.entrySet()) {
            Integer productId = Integer.valueOf(entry.getKey());
            Cart cart = gson.fromJson(entry.getValue(), Cart.class);
            Product product = productMapper.selectByPrimaryKey(productId);
            if(product!=null){
                CartProdutVo cartProdutVo = new CartProdutVo(
                        cart.getProductId(),
                        cart.getQuantity(),
                        product.getName(),
                        product.getSubtitle(),
                        product.getMainImage(),
                        product.getPrice(),
                        product.getStatus(),
                        product.getPrice().multiply(BigDecimal.valueOf(cart.getQuantity())),
                        product.getStock(),
                        cart.getSelected()
                );
                cartProdutVoList.add(cartProdutVo);
                if(!cart.getSelected()){
                    selectAll = false;
                }
                if(cart.getSelected()==true){
                    cartTotalPrice.add(product.getPrice().multiply(BigDecimal.valueOf(cart.getQuantity())));
                }

            }
            cartTotalQuantity +=cart.getQuantity();
        }
        //全选后的列表 有一个没选中就不是全选
        cartVo.setCartTotalPrice(cartTotalPrice);
        cartVo.setSelectedAll(selectAll);
        cartVo.setCartTotalQuantity(cartTotalQuantity);
        cartVo.setCartProductVoList(cartProdutVoList);
        return ResponseVo.success(cartVo);
    }

    @Override
    public ResponseVo<CartVo> Update(Integer uid, Integer productId, CartUpdateForm cartUpdateForm) {
        Cart cart;
        HashOperations<String, String, String> ops = redisTemplate.opsForHash();
        String key = String.format(CART_REDIS_KEY_TEMPLATE,uid);
        String value = ops.get(key, String.valueOf(productId));
        if(StringUtil.isNullOrEmpty(value)){
            return ResponseVo.error(ResponseEmnu.CART_PRODUCT_NOT_EXIST);
        }
        cart = gson.fromJson(value, Cart.class);
        if(cartUpdateForm.getQuantity()!=null
        && cartUpdateForm.getQuantity()>=0){
            cart.setQuantity(cartUpdateForm.getQuantity());
        }
        if(cartUpdateForm.getSelected()!=null){
            cart.setSelected(cartUpdateForm.getSelected());
        }
        ops.put(key,String.valueOf(productId),gson.toJson(cart));
        return list(uid);
    }
    //从购物车删除商品
    @Override
    public ResponseVo<CartVo> delete(Integer uid, Integer productId) {
        HashOperations<String, String, String> ops = redisTemplate.opsForHash();
        String key = String.format(CART_REDIS_KEY_TEMPLATE,uid);
        String value = ops.get(key, productId);
        //当物品不存在不做操作
        //当物品数量大于0时候 更新redis hash
        //当物体数量小于0的时候 直接从redis中删除
        if (StringUtil.isNullOrEmpty(value)) {
            return ResponseVo.error(ResponseEmnu.CART_PRODUCT_NOT_EXIST);
        }
//        Cart cart = gson.fromJson(value, Cart.class);
//        //从库中删除
//        if(cart.getQuantity()<=1){
//
//        }else{
//            //redis更新
//        }
        ops.delete(key,String.valueOf(productId));
        return list(uid);
    }

    @Override
    public ResponseVo<CartVo> selectAll(Integer uid) {
        String key = String.format(CART_REDIS_KEY_TEMPLATE,uid);
        HashOperations<String, Object, Object> ops = redisTemplate.opsForHash();
        List<Cart> carts = listForCart(uid);
        for (Cart cart : carts) {
            cart.setSelected(true);
            ops.put(key
                    , String.valueOf(cart.getProductId())
                    , gson.toJson(cart));
        }
        return list(uid);
    }

    @Override
    public ResponseVo<CartVo> unselectAll(Integer uid) {
        String key = String.format(CART_REDIS_KEY_TEMPLATE,uid);
        HashOperations<String, Object, Object> ops = redisTemplate.opsForHash();
        List<Cart> carts = listForCart(uid);
        for (Cart cart : carts) {
            cart.setSelected(false);
            ops.put(key
                    , String.valueOf(cart.getProductId())
                    , gson.toJson(cart));
        }
        return list(uid);
    }

    @Override
    public ResponseVo<Integer> summ(Integer uid) {
        List<Cart> carts = listForCart(uid);
        Integer sum = 0;
        for (Cart cart : carts) {
            sum+=cart.getQuantity();
        }
        return ResponseVo.success(sum);
    }
    public List<Cart> listForCart(Integer uid){
        String key = String.format(CART_REDIS_KEY_TEMPLATE,uid);
        HashOperations<String, Object, Object> ops = redisTemplate.opsForHash();
        List<Cart> res = new ArrayList<>();
        Set<Map.Entry<Object, Object>> set = ops.entries(key).entrySet();
        for (Map.Entry<Object, Object> entry : set) {
            res.add(gson.fromJson(String.valueOf(entry.getValue()),Cart.class));
        }
        return res;
    }

}
