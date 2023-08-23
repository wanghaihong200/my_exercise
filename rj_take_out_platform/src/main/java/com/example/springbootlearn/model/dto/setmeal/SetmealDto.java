package com.example.springbootlearn.model.dto.setmeal;

import com.example.springbootlearn.db.domain.ice.Setmeal;
import com.example.springbootlearn.db.domain.ice.SetmealDish;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: 王海虹
 * @create: 2022-11-28 11:29
 */
@Data
public class SetmealDto extends Setmeal {
    // 套餐关联菜品列表
    private List<SetmealDish> setmealDishes;
    // 套餐分类名称
    private String categoryName;
}
