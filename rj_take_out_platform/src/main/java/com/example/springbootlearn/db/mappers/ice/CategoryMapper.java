package com.example.springbootlearn.db.mappers.ice;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.example.springbootlearn.db.domain.ice.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author TCLDUSER
* @description 针对表【category(菜品及套餐分类)】的数据库操作Mapper
* @createDate 2022-11-04 15:54:19
* @Entity com.example.springbootlearn.db.domain.ice.Category
*/
@Mapper
@DS("ice")
public interface CategoryMapper extends BaseMapper<Category> {

}




