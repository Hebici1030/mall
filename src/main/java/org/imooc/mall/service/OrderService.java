package org.imooc.mall.service;

import com.github.pagehelper.PageInfo;
import org.imooc.mall.vo.OrderVo;
import org.imooc.mall.vo.ResponseVo;

import java.util.List;

public interface OrderService {
    ResponseVo<OrderVo> create(Integer uid,Integer shippingId);

    ResponseVo<PageInfo> list (Integer uid,Integer pageSize,Integer pageNum);

    ResponseVo<OrderVo> detail(Integer uid,Long orderNo);
}
