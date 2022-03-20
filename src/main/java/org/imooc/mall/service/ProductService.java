package org.imooc.mall.service;

import com.github.pagehelper.PageInfo;
import org.imooc.mall.pojo.Product;
import org.imooc.mall.vo.ProductDetailVo;
import org.imooc.mall.vo.ResponseVo;

import java.util.List;

public interface ProductService {
    ResponseVo<PageInfo> select(Integer categoryId, Integer pageNUm, Integer pageSize);
    ResponseVo<ProductDetailVo> detail(Integer productId);
}
