package org.imooc.mall.service.Impl;

import org.imooc.mall.consts.MallConst;
import org.imooc.mall.dao.CategoryMapper;
import org.imooc.mall.pojo.Category;
import org.imooc.mall.service.CategoryService;
import org.imooc.mall.vo.CategoryVO;
import org.imooc.mall.vo.ResponseVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    public ResponseVo<List<CategoryVO>> selectAll() {
        List<CategoryVO> categoryVOList = new ArrayList<>();
        List<Category> categorys = categoryMapper.selectAll();
        //找出parentID为0的作为主目录
        for(Category category:categorys){
            if(category.getParentId().equals(MallConst.ROOT_PARENT_ID)){
                CategoryVO categoryVO = new CategoryVO();
                BeanUtils.copyProperties(category,categoryVO);
                categoryVOList.add(categoryVO);
            }
        }
        //查找子目录
        findSubCategory(categoryVOList,categorys);
        //泛型接受结果
        return  ResponseVo.success(categoryVOList);
    }

    /**
     * 不懂此处的业务逻辑 机柜不会发生溢出吗
     * @param id
     * @param resule
     */
    @Override
    public void findSubCategoyId(Integer id, Set<Integer> resule) {
        List<Category> categories = categoryMapper.selectAll();
        findSubCategory(id,resule,categories);
    }

    private void findSubCategory(Integer id,Set<Integer> result,List<Category> categories){
        for(Category category:categories){
            if(category.getParentId().equals(id)){
                result.add(category.getId());
                findSubCategory(id,result,categories);
            }
        }
    }
    /**
     *
     * @param categoryVOList 父目录
     * @param categorys 全部商品
     */
    private void findSubCategory(List<CategoryVO> categoryVOList, List<Category> categorys) {
        for(CategoryVO categoryVO:categoryVOList){
            List<CategoryVO> subcategoryVOList = new ArrayList<>();
            for(Category category:categorys){
                if(categoryVO.getId().equals(category.getParentId())){
                    CategoryVO subCategoryVo = category2CategoryVo(category);
                    subcategoryVOList.add(subCategoryVo);
                }
            }

            categoryVO.setSubCategories(subcategoryVOList);
        }
    }

    private CategoryVO category2CategoryVo(Category category){
        CategoryVO categoryVO = new CategoryVO();
        BeanUtils.copyProperties(category,categoryVO);
        return categoryVO;
    }
}
