package org.imooc.mall.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.PrintStream;
import java.util.List;
@Data
@JsonInclude
public class CategoryVO {
    private Integer id;
    private Integer parentId;
    private String name;
    private Integer sortOrder;
    private List<CategoryVO> subCategories;

}
