package com.example.springbootlearn.db.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springbootlearn.db.domain.ice.Category;
import com.example.springbootlearn.db.domain.ice.Dish;
import com.example.springbootlearn.db.domain.ice.Setmeal;
import com.example.springbootlearn.db.service.CategoryService;
import com.example.springbootlearn.db.mappers.ice.CategoryMapper;
import com.example.springbootlearn.db.service.DishService;
import com.example.springbootlearn.db.service.SetmealService;
import com.example.springbootlearn.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
* @author TCLDUSER
* @description 针对表【category(菜品及套餐分类)】的数据库操作Service实现
* @createDate 2022-11-04 15:54:19
*/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
    implements CategoryService{
    @Autowired
    private DishService dishService;

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 根据id删除分类
     * @param id
     */
    @Override
    public Result remove(Long id) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId, id);

        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId, id);

        SetmealService setmealService = applicationContext.getBean(SetmealService.class);

        if (dishService.count(dishLambdaQueryWrapper) >0 || setmealService.count(setmealLambdaQueryWrapper)>0){
            return Result.fail("当前分类下关联了菜品 或 套餐，无法删除！");
        }else{
            this.removeById(id);
        }

        return Result.success("分类信息删除成功, id: "+id);
    }
}




