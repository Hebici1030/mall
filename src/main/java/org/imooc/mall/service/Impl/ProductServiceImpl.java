package org.imooc.mall.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.autoconfigure.PageHelperAutoConfiguration;
import com.github.pagehelper.autoconfigure.PageHelperProperties;
import org.imooc.mall.dao.ProductMapper;
import org.imooc.mall.emnu.ProductStatusEnum;
import org.imooc.mall.emnu.ResponseEmnu;
import org.imooc.mall.pojo.Product;
import org.imooc.mall.pojo.ProductWithBLOBs;
import org.imooc.mall.service.CategoryService;
import org.imooc.mall.service.ProductService;
import org.imooc.mall.vo.ProductDetailVo;
import org.imooc.mall.vo.ProductVO;
import org.imooc.mall.vo.ResponseVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProductServiceImpl implements ProductService {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductMapper productMapper;
    @Override
    public ResponseVo<PageInfo> select(Integer categoryId, Integer pageNUm, Integer pageSize) {
        //通过所属目录查找对应的商品
        Set<Integer> categoryIdSet = new HashSet<>();
        if(categoryId!=null){
            categoryService.findSubCategoyId(categoryId,categoryIdSet);
            categoryIdSet.add(categoryId);
        }
        PageHelper.startPage(pageNUm,pageSize);
        List<Product> products = productMapper.selectByCategoryIdSet(categoryIdSet);
        List<ProductVO> productVOS = new ArrayList<>();
        for(Product product:products){
            ProductVO productVO = new ProductVO();
            BeanUtils.copyProperties(product,productVO);
            productVOS.add(productVO);
        }
        PageInfo productPageInfo = new PageInfo(products);
        productPageInfo.setList(productVOS);
        return ResponseVo.success(productPageInfo);
    }

    @Override
    public ResponseVo<ProductDetailVo> detail(Integer productId) {
        Product product = productMapper.selectByPrimaryKey(productId);
        //只对确定小条件判断
        if(product.getStatus().equals(ProductStatusEnum.OFF_SALE)||
                product.getStatus().equals(ProductStatusEnum.DELETE)){
            ResponseVo.error(ResponseEmnu.PRODUCT_OFF_SALE_OR_DELETE);
        }
        ProductDetailVo productDetailVo = new ProductDetailVo();
        BeanUtils.copyProperties(product,productDetailVo);
        //限定数量
        productDetailVo.setStock(product.getStock()>100?100:product.getStock());
        return ResponseVo.success(productDetailVo);
    }
}
