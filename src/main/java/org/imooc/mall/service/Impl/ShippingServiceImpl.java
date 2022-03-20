package org.imooc.mall.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.imooc.mall.dao.ShippingMapper;
import org.imooc.mall.emnu.ResponseEmnu;
import org.imooc.mall.form.ShippingForm;
import org.imooc.mall.pojo.Shipping;
import org.imooc.mall.service.ShippingService;
import org.imooc.mall.vo.ResponseVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShippingServiceImpl implements ShippingService {
    @Autowired
    private ShippingMapper shippingMapper;
    @Override
    public ResponseVo<Map<String,Integer>> add(Integer uid, ShippingForm shippingForm) {
        Shipping shipping = new Shipping();
        BeanUtils.copyProperties(shippingForm,shipping);
        int row = shippingMapper.insertSelective(shipping);
        if(row == 0){
            return ResponseVo.error(ResponseEmnu.ERROE);
        }
        Map<String,Integer> map = new HashMap<>();
        map.put("shippingid",shipping.getId());

        return ResponseVo.success(map);
    }

    @Override
    public ResponseVo delete(Integer uid, Integer shipping) {
        int row=shippingMapper.deleteByIdAndUid(uid,shipping);
        if(row==0){
            return ResponseVo.error(ResponseEmnu.DELETE_SHIPPING_FAIL);
        }
        return ResponseVo.success();
    }

    @Override
    public ResponseVo update(Integer uid, Integer shippingId,ShippingForm shippingForm) {
        Shipping shipping = new Shipping();
        BeanUtils.copyProperties(shippingForm,shipping);
        shipping.setUserId(uid);
        shipping.setId(shippingId);
        /**
         * 是否需要删除Date的sql语句判断
         */
        int row = shippingMapper.updateByPrimaryKeySelective(shipping);
        if(row==0){
            return ResponseVo.error(ResponseEmnu.ERROE);
        }
        return ResponseVo.success();
    }

    @Override
    public ResponseVo<PageInfo> list(Integer uid, Integer pagesize, Integer PageNum) {
        PageHelper.startPage(PageNum,pagesize);
        List<Shipping> list = shippingMapper.selectByUid(uid);
        PageInfo pageInfo = new PageInfo(list);
        return ResponseVo.success(pageInfo);
    }
}
