package com.example.springbootlearn.db.mappers.ice;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.example.springbootlearn.db.domain.ice.SetmealDish;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author TCLDUSER
* @description 针对表【setmeal_dish(套餐菜品关系)】的数据库操作Mapper
* @createDate 2022-11-07 11:16:22
* @Entity com.example.springbootlearn.db.domain.ice.SetmealDish
*/
@Mapper
@DS("ice")
public interface SetmealDishMapper extends BaseMapper<SetmealDish> {

}




