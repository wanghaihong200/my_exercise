package com.example.springbootlearn.db.service;

import com.example.springbootlearn.db.domain.ice.Dish;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springbootlearn.model.dto.dish.DishDto;

/**
* @author TCLDUSER
* @description 针对表【dish(菜品管理)】的数据库操作Service
* @createDate 2022-11-07 11:15:27
*/
public interface DishService extends IService<Dish> {
    public Boolean saveWithFlavor(DishDto dishDto);

    public DishDto getByIdWithFlavor(Long id);

    public Boolean updateWithFlavor(DishDto dishDto);
}
