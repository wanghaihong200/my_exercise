package com.example.springbootlearn.db.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springbootlearn.db.domain.ice.Category;
import com.example.springbootlearn.db.domain.ice.Dish;
import com.example.springbootlearn.db.domain.ice.DishFlavor;
import com.example.springbootlearn.db.service.CategoryService;
import com.example.springbootlearn.db.service.DishFlavorService;
import com.example.springbootlearn.db.service.DishService;
import com.example.springbootlearn.db.mappers.ice.DishMapper;
import com.example.springbootlearn.model.Result;
import com.example.springbootlearn.model.dto.dish.DishDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author TCLDUSER
 * @description 针对表【dish(菜品管理)】的数据库操作Service实现
 * @createDate 2022-11-07 11:15:27
 */
@Service
@DS("ice")
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish>
        implements DishService {
    @Autowired
    private DishFlavorService dishFlavorService;

    @Autowired
    private ApplicationContext applicationContext;
    //private CategoryService categoryService;

    @Override
    @Transactional
    public Boolean saveWithFlavor(DishDto dishDto) {
        // 保存菜品信息到 菜品表dish
        this.save(dishDto);

        Long dishId = dishDto.getId();
        List<DishFlavor> flavorList = dishDto.getFlavors();
        flavorList = flavorList.stream().map(flavor -> {
            flavor.setDishId(dishId);
            return flavor;
        }).collect(Collectors.toList());
        // 保存菜品口味数据 到 dish_flavor
        return dishFlavorService.saveBatch(flavorList);
    }

    @Override
    public DishDto getByIdWithFlavor(Long id) {
        Dish dish = this.getById(id);
        DishDto dishDto = new DishDto();
        if (dish != null) {
            BeanUtils.copyProperties(dish, dishDto);
            // 为了解决 AutoWired categoryService 的 循环依赖
            CategoryService categoryService = applicationContext.getBean(CategoryService.class);
            String categoryName = categoryService.getById(dishDto.getCategoryId()).getName();
            // 添加 categoryName
            dishDto.setCategoryName(categoryName);

            LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(DishFlavor::getDishId, dishDto.getId())
                    .eq(DishFlavor::getIsDeleted, 0)
                    .orderByDesc(DishFlavor::getUpdateTime);

            List<DishFlavor> dishFlavorList = dishFlavorService.list(queryWrapper);
            dishDto.setFlavors(dishFlavorList);
        }
        return dishDto;
    }

    /**
     * @param dishDto
     */
    @Override
    @Transactional
    public Boolean updateWithFlavor(DishDto dishDto) {
        // 更新dish表基本信息
        Boolean updateDish = this.updateById(dishDto);

        // 清理当前的 dishFlavor数据
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId, dishDto.getId());
        dishFlavorService.remove(queryWrapper);

        // 保存新的 dishFlavor数据
        Long dishId = dishDto.getId();
        List<DishFlavor> dishFlavorList = dishDto.getFlavors();
        dishFlavorList = dishFlavorList.stream().map((item) -> {
            item.setDishId(dishId);
            return item;
        }).collect(Collectors.toList());
        Boolean updateFlavorList = dishFlavorService.saveBatch(dishFlavorList);

        return updateDish && updateFlavorList;
    }
}




