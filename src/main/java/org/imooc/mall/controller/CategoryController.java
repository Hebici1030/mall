package org.imooc.mall.controller;

import org.imooc.mall.service.CategoryService;
import org.imooc.mall.vo.CategoryVO;
import org.imooc.mall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping("/categories")
    public ResponseVo<List<CategoryVO>> selectall()
    {
        return categoryService.selectAll();
    }
}
