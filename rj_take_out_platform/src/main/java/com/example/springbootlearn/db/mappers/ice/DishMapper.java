package com.example.springbootlearn.db.mappers.ice;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.example.springbootlearn.db.domain.ice.Dish;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author TCLDUSER
* @description 针对表【dish(菜品管理)】的数据库操作Mapper
* @createDate 2022-11-07 11:15:27
* @Entity com.example.springbootlearn.db.domain.ice.Dish
*/
@Mapper
@DS("ice")
public interface DishMapper extends BaseMapper<Dish> {

}




