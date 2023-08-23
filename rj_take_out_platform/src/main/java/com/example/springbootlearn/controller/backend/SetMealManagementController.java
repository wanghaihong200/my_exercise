package com.example.springbootlearn.controller.backend;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springbootlearn.db.domain.ice.Dish;
import com.example.springbootlearn.db.domain.ice.Setmeal;
import com.example.springbootlearn.db.domain.ice.SetmealDish;
import com.example.springbootlearn.db.service.CategoryService;
import com.example.springbootlearn.db.service.DishService;
import com.example.springbootlearn.db.service.SetmealDishService;
import com.example.springbootlearn.db.service.SetmealService;
import com.example.springbootlearn.model.Result;
import com.example.springbootlearn.model.dto.setmeal.SetmealDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: 套餐管理
 * @author: 王海虹
 * @create: 2022-11-28 11:00
 */
@RestController
@RequestMapping("/setmeal")
@Slf4j
@Api(tags= "套餐管理")
public class SetMealManagementController {
    @Autowired
    private SetmealService setmealService;

    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private DishService dishService;

    @GetMapping("/page")
    @ApiOperation("套餐管理分页查询")
    public Result pageSetMeal(@RequestParam("page") int page, int pageSize, @RequestParam(value = "name", required = false) String setmealName) {
        Page<SetmealDto> setmealDtoPage = setmealService.pageSetmeal(page, pageSize, setmealName);

        return Result.success(setmealDtoPage);
    }

    @PostMapping
    public Result saveSetmealWithDish(@RequestBody SetmealDto setmealDto) {
        if (setmealService.saveSetmealWithDish(setmealDto)) {
            return Result.success("套餐保存成功！");
        }
        return Result.fail("套餐保存失败！");
    }

    /**
     * @param: status： 0--停售，1--启售， ids: setmeal.id 数组
     * @return:
     * @date: 2022/11/29
     */
    @PostMapping(path = "/status/{status}")
    @ApiOperation("更新套餐状态")
    public Result changeSetmeal(@PathVariable("status") int status, @RequestParam(value = "ids", required = false) List<Long> ids) {
        if (setmealService.changeSetmealWithDishStatus(status, ids)) {
            String msg = status == 0 ? "套餐停售成功！" : "套餐起售成功！";
            return Result.success(msg);
        }
        return Result.fail("套餐状态更新失败！");
    }

    @DeleteMapping
    @ApiOperation("删除套餐及套餐关联的菜品")
    public Result deleteSetmeal(@RequestParam List<Long> ids) {
        if (setmealService.deleteSetmealWithDish(ids)) {
            return Result.success("删除套餐及套餐关联的菜品成功！");
        }
        return Result.fail("套餐处于启售状态，或其他异常导致 删除失败！");
    }

    @GetMapping(path = "/{setmealId}")
    @ApiOperation("根据id 获取套餐及关联菜品信息")
    public Result querySetmealWithDish(@PathVariable("setmealId") Long setmealId) {
        //SetmealDto setMealDto = setmealService.querySetmealWithDish(setmealId);
        SetmealDto setMealDto = setmealService.querySetmealWithDish2(setmealId);

        if (setMealDto != null) {
            return Result.success(setMealDto);
        }

        return Result.fail("获取套餐及关联菜品信息 失败！");
    }

    @GetMapping("/list")
    @ApiOperation("查询套餐关联的菜品")
    public Result queryDishListBySetmeal(Long categoryId, int status){
        // 通过 categoryId 获取 套餐数据
        LambdaQueryWrapper<Setmeal> setmealQuery = new LambdaQueryWrapper<>();
        setmealQuery.eq(Setmeal::getCategoryId, categoryId)
                .eq(Setmeal::getStatus, status);

        Setmeal setmeal = setmealService.getOne(setmealQuery);
        // 通过套餐Id 获取 关联菜品
        if (setmeal != null){
            Long setmealId = setmeal.getId();
            LambdaQueryWrapper<SetmealDish> setmealDishLambdaQueryWrapper = new LambdaQueryWrapper<>();
            setmealDishLambdaQueryWrapper.eq(SetmealDish::getSetmealId, String.valueOf(setmealId) );
            List<SetmealDish> setmealDishes = setmealDishService.list(setmealDishLambdaQueryWrapper);
            if (setmealDishes != null){
                List<Long> dishIdList = setmealDishes.stream().map(item-> Long.valueOf(item.getDishId())).collect(Collectors.toList());

                LambdaQueryWrapper<Dish> dishLambdaQuery = new LambdaQueryWrapper<>();
                dishLambdaQuery.in(Dish::getId, dishIdList);
                return Result.success(dishService.list(dishLambdaQuery));
            }
        }
        return Result.fail("获取套餐关联的菜品失败");
    }


    @PutMapping
    @ApiOperation("修改套餐及其关联的菜品信息")
    public Result updateSetmealWithDish(@RequestBody SetmealDto setmealDto){
        return setmealService.updateSetmealWithDish(setmealDto);
    }
}
