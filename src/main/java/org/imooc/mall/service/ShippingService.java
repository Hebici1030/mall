package org.imooc.mall.service;

import com.github.pagehelper.PageInfo;
import com.sun.javafx.collections.MappingChange;
import org.imooc.mall.form.ShippingForm;
import org.imooc.mall.vo.ResponseVo;

import javax.print.DocFlavor;
import java.util.Map;

public interface ShippingService {
    ResponseVo<Map<String,Integer>> add(Integer uid, ShippingForm shippingForm);

    ResponseVo delete(Integer uid,Integer shipping);

    ResponseVo update(Integer uid,Integer shipping,ShippingForm shippingForm);

    ResponseVo<PageInfo> list(Integer uid,Integer pagesize,Integer PageNum);

}
