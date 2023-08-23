package com.example.springbootlearn.controller.backend;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springbootlearn.db.domain.ice.Category;
import com.example.springbootlearn.db.domain.ice.Dish;
import com.example.springbootlearn.db.domain.ice.DishFlavor;
import com.example.springbootlearn.db.service.CategoryService;
import com.example.springbootlearn.db.service.DishFlavorService;
import com.example.springbootlearn.db.service.DishService;
import com.example.springbootlearn.model.Result;
import com.example.springbootlearn.model.dto.dish.DishDto;
import com.example.springbootlearn.util.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @description: 菜品crud
 * @author: 王海虹
 * @create: 2022-11-08 19:49
 */

@RestController
@RequestMapping("/dish")
@Api(tags = "菜品管理页面")
@Slf4j
public class DishManagementController {
    @Autowired
    private DishService dishService;

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    @ApiOperation("新增菜品")
    public Result saveDish(@RequestBody DishDto dishDto) {
        if (dishService.saveWithFlavor(dishDto)) {
            return Result.success("新增菜品成功！");
        }
        return Result.fail("新增菜品失败！");
    }

    /**
     * 菜品信息分页查询
     *
     * @param:
     * @return:
     * @date: 2022/11/23
     */
    @GetMapping("/page")
    @ApiOperation("菜品信息分页查询")
    public Result page(int page, int pageSize, String name) {
        // 构造分页构造器对象
        Page<Dish> pageInfo = new Page<>(page, pageSize);
        Page<DishDto> dishDtoPage = new Page<>();

        // 查询条件
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(name != null, Dish::getName, name)
                .orderByDesc(Dish::getUpdateTime);
        // 执行分页查询
        dishService.page(pageInfo, queryWrapper);

        // 对象拷贝
        BeanUtils.copyProperties(pageInfo, dishDtoPage, "records");

        List<Dish> records = pageInfo.getRecords();
        List<DishDto> list = records.stream().map((item) -> {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);
            Long categoryId = item.getCategoryId(); // 获取分类ID
            // 根据id分类对象
            Category category = categoryService.getById(categoryId);

            if (category != null){
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }
            return dishDto;
        }).collect(Collectors.toList());

        dishDtoPage.setRecords(list);
        return Result.success(dishDtoPage);
    }

    @GetMapping(path="/{dishId}")
    @ApiOperation("获取菜品信息")
    public Result getDishDto(@PathVariable("dishId") Long dishId){
        DishDto dishDto = dishService.getByIdWithFlavor(dishId);
        if (dishDto!=null){
            return Result.success(dishDto);
        }
        return Result.fail("菜品不存在！");
    }


    @PutMapping
    public Result updateDishDto(@RequestBody DishDto dishDto){
        if (dishService.updateWithFlavor(dishDto)){
            return Result.success("更新菜品成功！");
        }
        return Result.fail("更新菜品失败！");
    }

    @GetMapping("/list")
    @ApiOperation("获取菜品列表")
    public Result getDishList(Long categoryId, @RequestParam(value = "name",required = false) String name){
        LambdaQueryWrapper<Dish> dishQueryWrapper = new LambdaQueryWrapper<>();
        dishQueryWrapper.eq(categoryId != null ,Dish::getCategoryId, categoryId)
                .like(name != null, Dish::getName, name)
                .eq(Dish::getStatus, 1)
                .orderByDesc(Dish::getUpdateTime);
        List<Dish> dishes = dishService.list(dishQueryWrapper);
        if (!dishes.isEmpty()){
            return Result.success(dishes);
        }
        return Result.fail("菜品列表获取失败！");
    }

}
