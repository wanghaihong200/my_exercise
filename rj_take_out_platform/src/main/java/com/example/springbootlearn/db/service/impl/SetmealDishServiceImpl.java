package com.example.springbootlearn.db.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springbootlearn.db.domain.ice.SetmealDish;
import com.example.springbootlearn.db.service.SetmealDishService;
import com.example.springbootlearn.db.mappers.ice.SetmealDishMapper;
import org.springframework.stereotype.Service;

/**
* @author TCLDUSER
* @description 针对表【setmeal_dish(套餐菜品关系)】的数据库操作Service实现
* @createDate 2022-11-07 11:16:22
*/
@Service
@DS("ice")
public class SetmealDishServiceImpl extends ServiceImpl<SetmealDishMapper, SetmealDish>
    implements SetmealDishService{

}




