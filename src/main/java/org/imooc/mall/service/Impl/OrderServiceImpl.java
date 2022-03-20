package org.imooc.mall.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.imooc.mall.dao.*;
import org.imooc.mall.emnu.OrderStatusEnum;
import org.imooc.mall.emnu.PaymentType;
import org.imooc.mall.emnu.ProductStatusEnum;
import org.imooc.mall.emnu.ResponseEmnu;
import org.imooc.mall.pojo.*;
import org.imooc.mall.service.CartService;
import org.imooc.mall.service.OrderService;
import org.imooc.mall.vo.OrderItemVo;
import org.imooc.mall.vo.OrderVo;
import org.imooc.mall.vo.ResponseVo;
import org.junit.jupiter.api.MethodOrderer;
import org.mockito.internal.matchers.Or;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.xml.crypto.Data;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ShippingMapper shippingMapper;
    @Autowired
    private CartService cartService;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderItmeMapper orderItmeMapper;
    @Override
    @Transactional
    public ResponseVo<OrderVo> create(Integer uid, Integer shippingId) {
        //收货地址效验
        Shipping shipping = shippingMapper.selectByUidAndShippingId(uid, shippingId);
        if(shipping==null){
            return  ResponseVo.error(ResponseEmnu.SHIPPING_NOT_EXIST);

        }

//        List<Cart> carts = cartService.listForCart(uid);
//        //通过set保存carts中的productIds
//        if(carts.isEmpty()){
//            return ResponseVo.error(ResponseEmnu.CART_SELECTED_IS_EMPTY);
//        }
//        Set<Integer> productIdSet = new HashSet<>();
//        for (Cart cart : carts) {
//            productIdSet.add(cart.getProductId());
//        }
//        productMapper.selectByPrimaryKey( );
        // 获取购物车（查看是否有商品库存）
        List<Cart> cartList = cartService.listForCart(uid).stream()
                .filter(Cart::getSelected)
                .collect(Collectors.toList());
        if(CollectionUtils.isEmpty(cartList)){
            return ResponseVo.error(ResponseEmnu.CART_SELECTED_IS_EMPTY);
        }
        Set<Integer> productIdSet = cartList.stream().map(Cart::getProductId)
                .collect(Collectors.toSet());
        List<Product> products = productMapper.selectByProductIdSet(productIdSet);
        Map<Integer, Product> map = products.stream()
                .collect(Collectors.toMap(Product::getId, product -> product));
        Long OrderNo = buildOrderNo();
        List<OrderItme> orderItmeList = new ArrayList<>();
        for (Cart cart : cartList) {
            //根据productId查询数据库
            Product product = map.get(cart.getProductId());
            //是否有商品
            if(product==null){
                return ResponseVo.error(ResponseEmnu.PRODUCT_NOT_EXIST,
                        "商品不存在. productId="+cart.getProductId());
            }
            //商品上下架状态
            if(!product.getStatus().equals(ProductStatusEnum.ON_SALE.getCode())){
                return ResponseVo.error(ResponseEmnu.PRODUCT_OFF_SALE_OR_DELETE,"商品下架或删除 productId = "+ cart.getProductId());
            }
            //库存是否从足
            if(product.getStock()<cart.getQuantity()){
                return ResponseVo.error(ResponseEmnu.PRODUCT_STOCK_ERROE,
                        "库存不正确"+product.getId());
            }

            OrderItme orderItme = bulidOrderItem(uid, OrderNo, cart.getQuantity(), product);
            orderItmeList.add(orderItme);
            //减少库存
            product.setStock(product.getStock() - cart.getQuantity());
            int rowForProduct = productMapper.updateByPrimaryKeySelective(product);
            if(rowForProduct<=0){
                return ResponseVo.error(ResponseEmnu.ERROE);
            }

        }
        Order order = buildOrder(uid, OrderNo, shippingId, orderItmeList);
        //计算总价格（只计算选中的商品）

        //生成订单入库 （事务性质的）(订单是否完成？)
        int row = orderMapper.insertSelective(order);
        if(row<=0){
            return ResponseVo.error(ResponseEmnu.ERROE);
        }

        int rowForOrderItem = orderItmeMapper.batchSelective(orderItmeList);
        if(rowForOrderItem<=0){
            return ResponseVo.error(ResponseEmnu.ERROE);
        }
        //减库存

        //更新购物车（选中的商品）

        for(Cart cart:cartList){
            cartService.delete(uid,cart.getProductId());
        }

        //  Redis事务（打包命令）

        //构造OrderVo
        OrderVo orderVo = builOrderVo(order, orderItmeList, shipping);
        return ResponseVo.success(orderVo);
    }

    @Override
    public ResponseVo<PageInfo> list(Integer uid, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum,pageSize);
        List<Order> orderList = orderMapper.selectByUid(uid);
        Set<Long> orderNoSet = new HashSet<>();
        Set<Integer> shippingIdSet = new HashSet<>();
        for (Order order : orderList) {
            orderNoSet.add(order.getOrderNo());
            shippingIdSet.add(order.getShippingId());
        }
        List<OrderItme> orderItmeList = orderItmeMapper.selectByOrderNoSet(orderNoSet);
        List<Shipping> shippingList = shippingMapper.selectByIdSet(shippingIdSet);
        PageInfo pageInfo = new PageInfo(orderList);
        pageInfo.setList();
        return null;
    }

    @Override
    public ResponseVo<OrderVo> detail(Integer uid, Long orderNo) {
        return null;
    }



    private OrderVo builOrderVo(Order order, List<OrderItme> orderItmeList, Shipping shipping) {
        OrderVo orderVo = new OrderVo();
        BeanUtils.copyProperties(order,orderVo);
        List<OrderItemVo> orderItemVoList = new ArrayList<>();
        for (OrderItme orderItme : orderItmeList) {
            OrderItemVo orderItemVo = new OrderItemVo();
            BeanUtils.copyProperties(orderItme,orderItemVo);
            orderItemVoList.add(orderItemVo);
        }
        orderVo.setOrderItemVoList(orderItemVoList);
        orderVo.setShippingId(shipping.getId());
        orderVo.setShippingVo(shipping);
        return orderVo;
    }

    private Order buildOrder(Integer uid,
                             Long OrderNo,
                             Integer shippingId,
                             List<OrderItme> orderItmeList
                             ) {
        Order order = new Order();
        order.setOrderNo(OrderNo);
        order.setShippingId(shippingId);
        order.setUserId(uid);
        BigDecimal payment = orderItmeList.stream().map(OrderItme::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setPayment(payment);
        order.setPaymentType(PaymentType.PAYMENT_TYPE.getCode());
        order.setStatus(OrderStatusEnum.NO_PAY.getCode());
        order.setPostage(0);
        return order;
    }

    private Long buildOrderNo() {
        return System.currentTimeMillis() + new Random().nextInt(999);
    }

    private OrderItme bulidOrderItem(Integer uid,Long OrderNo,Integer quantity,Product product) {
        OrderItme orderItme = new OrderItme();
        orderItme.setId(uid);
        orderItme.setOrderNo(OrderNo);
        orderItme.setProductId(product.getId());
        orderItme.setProductName(product.getName());
        orderItme.setProductImage(product.getMainImage());
        orderItme.setCurrentUnitPrice(product.getPrice());
        orderItme.setQuantity(quantity);
        orderItme.setTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
        return orderItme;
    }
}
