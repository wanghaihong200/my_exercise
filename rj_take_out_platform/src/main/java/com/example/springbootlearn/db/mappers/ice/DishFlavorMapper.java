package com.example.springbootlearn.db.mappers.ice;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.example.springbootlearn.db.domain.ice.DishFlavor;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author TCLDUSER
* @description 针对表【dish_flavor(菜品口味关系表)】的数据库操作Mapper
* @createDate 2022-11-07 11:15:59
* @Entity com.example.springbootlearn.db.domain.ice.DishFlavor
*/
@Mapper
@DS("ice")
public interface DishFlavorMapper extends BaseMapper<DishFlavor> {

}




