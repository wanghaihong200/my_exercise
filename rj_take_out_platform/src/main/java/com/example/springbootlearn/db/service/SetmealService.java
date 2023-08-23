package com.example.springbootlearn.db.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springbootlearn.db.domain.ice.Setmeal;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springbootlearn.model.Result;
import com.example.springbootlearn.model.dto.setmeal.SetmealDto;

import java.util.List;

/**
* @author TCLDUSER
* @description 针对表【setmeal(套餐)】的数据库操作Service
* @createDate 2022-11-07 11:16:16
*/
public interface SetmealService extends IService<Setmeal> {
    public Boolean saveSetmealWithDish(SetmealDto setmealDto);

    public Boolean changeSetmealWithDishStatus(int status,List<Long> ids);

    public Boolean deleteSetmealWithDish(List<Long> ids);

    public Result updateSetmealWithDish(SetmealDto setmealDto);

    public Page<SetmealDto> pageSetmeal(int page, int pageSize, String setmealName);

    public SetmealDto querySetmealWithDish(Long setmealId);

    public SetmealDto querySetmealWithDish2(Long setmealId);
}
