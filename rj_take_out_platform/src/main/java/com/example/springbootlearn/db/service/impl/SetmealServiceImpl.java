package com.example.springbootlearn.db.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springbootlearn.db.domain.ice.Setmeal;
import com.example.springbootlearn.db.domain.ice.SetmealDish;
import com.example.springbootlearn.db.mappers.ice.SetmealDishMapper;
import com.example.springbootlearn.db.service.CategoryService;
import com.example.springbootlearn.db.service.SetmealDishService;
import com.example.springbootlearn.db.service.SetmealService;
import com.example.springbootlearn.db.mappers.ice.SetmealMapper;
import com.example.springbootlearn.model.Result;
import com.example.springbootlearn.model.dto.setmeal.SetmealDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author TCLDUSER
 * @description 针对表【setmeal(套餐)】的数据库操作Service实现
 * @createDate 2022-11-07 11:16:16
 */
@Service
@DS("ice")
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal>
        implements SetmealService {
    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SetmealDishMapper setmealDishMapper;

    @Autowired
    private SetmealMapper setmealMapper;

    /**获取套餐及关联菜品信息
     * @param setmealId
     * @return
     */
    @Override
    public SetmealDto querySetmealWithDish(Long setmealId) {
        Setmeal setmeal = this.getById(setmealId);
        SetmealDto setMealDto = new SetmealDto();

        BeanUtils.copyProperties(setmeal, setMealDto);
        // 获取 setmealDishes
        LambdaQueryWrapper<SetmealDish> setmealDishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        System.out.println("setmealId: "+ setmealId);
        setmealDishLambdaQueryWrapper.eq(setmealId != null, SetmealDish::getSetmealId, Long.toString(setmealId))
                .eq(setmealId != null, SetmealDish::getIsDeleted, 0);
        List<SetmealDish> setmealDishes = setmealDishService.list(setmealDishLambdaQueryWrapper);

        setMealDto.setSetmealDishes(setmealDishes);

        return setMealDto;
    }

    /**获取套餐及关联菜品信息， 使用mybatis原生xml方式获取 多表数据
     * @param setmealId
     * @return
     */
    @Override
    public SetmealDto querySetmealWithDish2(Long setmealId) {
        // 获取setmeal 并 复制给 setmealDto
        Setmeal setmeal = setmealMapper.queryById(setmealId);
        SetmealDto setmealDto = new SetmealDto();
        BeanUtils.copyProperties(setmeal, setmealDto);

        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("setmeal_id", String.valueOf(setmealId));
        queryMap.put("is_deleted", 0);
        List<SetmealDish> setmealDishes = setmealDishMapper.selectByMap(queryMap);

        setmealDto.setSetmealDishes(setmealDishes);


        return setmealDto;
    }

    /**
     * 修改套餐及其关联的菜品信息
     *
     * @param setmealDto
     * @return
     */
    @Override
    @Transactional
    public Result updateSetmealWithDish(SetmealDto setmealDto) {
        // 更新 套餐信息
        Boolean updateMeal = this.updateById(setmealDto);

        Long setmealId = setmealDto.getId();

        //根据 setmealId 删除 当前套餐下的菜品
        LambdaQueryWrapper<SetmealDish> setmealDishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealDishLambdaQueryWrapper.eq(SetmealDish::getSetmealId, String.valueOf(setmealId));
        List<SetmealDish> setmealDishes = setmealDishService.list(setmealDishLambdaQueryWrapper);
        setmealDishService.removeBatchByIds(setmealDishes);

        // 给 SetmealDish 补充 setmealId,保存套餐下的关联菜品信息
        List<SetmealDish> setmealDishList = setmealDto.getSetmealDishes();
        setmealDishList = setmealDishList.stream().map(item->{
            item.setSetmealId(String.valueOf(setmealId));
            return item;
        }).collect(Collectors.toList());
        Boolean saveSetmealDish = setmealDishService.saveBatch(setmealDishList);

        if(updateMeal && saveSetmealDish){
            return Result.success("修改套餐及关联的菜品信息成功！");
        }
        return Result.fail("修改套餐及关联的菜品信息失败！");
    }

    /**
     * 根据id 套餐及套餐下的菜品关联，若套餐处于 启售状态，则不能删除
     *
     * @param ids
     * @return
     */
    @Override
    @Transactional
    public Boolean deleteSetmealWithDish(List<Long> ids) {
        List<Setmeal> setmeals = new ArrayList<>();
        // 查询ids 对应的套餐，将套餐的 isDeleted字段置为1
        for (int i = 0; i < ids.size(); i++) {
            Setmeal setmeal = this.getById(ids.get(i));
            if (setmeal != null) {
                if (setmeal.getStatus() != 1) {
                    setmeal.setIsDeleted(1);
                } else {
                    return false;
                }
            }
            setmeals.add(setmeal);
        }
        List<String> idList  = ids.stream().map(String::valueOf).collect(Collectors.toList());

        // 删除 套餐下的关联菜品
        LambdaQueryWrapper<SetmealDish> setmealDishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealDishLambdaQueryWrapper.in(SetmealDish::getSetmealId, idList);

        List<SetmealDish> setmealDishes = setmealDishService.list(setmealDishLambdaQueryWrapper);
        setmealDishes = setmealDishes.stream().map(item -> {
            item.setIsDeleted(1);
            return item;
        }).collect(Collectors.toList());

        return this.updateBatchById(setmeals) && setmealDishService.updateBatchById(setmealDishes);
    }

    /**
     * 保存套餐信息 及套餐下的菜品信息
     *
     * @param setmealDto
     * @return
     */
    @Override
    @Transactional
    public Boolean saveSetmealWithDish(SetmealDto setmealDto) {
        // 保存套餐数据
        Boolean saveSetmeal = this.save(setmealDto);
        Long setmealId = setmealDto.getId();

        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes = setmealDishes.stream().map(item -> {
            item.setSetmealId(String.valueOf(setmealId));
            return item;
        }).collect(Collectors.toList());
        Boolean saveSetmealDishes = setmealDishService.saveBatch(setmealDishes);
        return saveSetmeal && saveSetmealDishes;
    }

    /**
     * 根据id修改 套餐及套餐里菜品 的状态
     *
     * @param ids
     * @return
     */
    @Override
    @Transactional
    public Boolean changeSetmealWithDishStatus(int status, List<Long> ids) {
        List<Setmeal> setmeals = new ArrayList<>();
        List<SetmealDish> setmealDishes = new ArrayList<>();
        for (int i = 0; i < ids.size(); i++) {
            Setmeal setmeal = this.getById(ids.get(i));
            setmeal.setStatus(status);
            setmeals.add(setmeal);

            LambdaQueryWrapper<SetmealDish> setmealDishQueryWrapper = new LambdaQueryWrapper<>();
            setmealDishQueryWrapper.eq(SetmealDish::getSetmealId, Long.toString(ids.get(i)));

            //List<SetmealDish> setmealDishList = setmealDishService.list(setmealDishQueryWrapper);
            //setmealDishList = setmealDishList.stream().map(item->{
            //    item.setIsDeleted(1);
            //    return item;
            //}).collect(Collectors.toList());
            //setmealDishes.addAll(setmealDishList);
        }

        return this.updateBatchById(setmeals);
    }

    /**
     * @param page
     * @param pageSize
     * @param setmealName
     * @return
     */
    @Override
    public Page<SetmealDto> pageSetmeal(int page, int pageSize, String setmealName) {
        Page<Setmeal> setmealPage = new Page<>(page, pageSize);
        Page<SetmealDto> setmealDtoPage = new Page<>(page, pageSize);

        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Setmeal::getUpdateTime)
                .eq(Setmeal::getIsDeleted, 0)
                .like(setmealName != null, Setmeal::getName, setmealName);
        this.page(setmealPage, queryWrapper);

        BeanUtils.copyProperties(setmealPage, setmealDtoPage, "records");
        List<SetmealDto> records = setmealPage.getRecords().stream().map(item -> {
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(item, setmealDto);

            Long categoryId = setmealDto.getCategoryId();
            String categoryName = categoryService.getById(categoryId).getName();
            setmealDto.setCategoryName(categoryName);

            return setmealDto;
        }).collect(Collectors.toList());

        setmealDtoPage.setRecords(records);

        return setmealDtoPage;
    }
}




