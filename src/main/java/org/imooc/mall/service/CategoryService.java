package org.imooc.mall.service;

import org.imooc.mall.vo.CategoryVO;
import org.imooc.mall.vo.ResponseVo;

import java.util.List;
import java.util.Set;

public interface CategoryService {
    public ResponseVo<List<CategoryVO>> selectAll();
    public void findSubCategoyId(Integer id, Set<Integer> resule);
}
