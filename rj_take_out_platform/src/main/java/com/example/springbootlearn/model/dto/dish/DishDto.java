package com.example.springbootlearn.model.dto.dish;

import com.example.springbootlearn.db.domain.ice.Dish;
import com.example.springbootlearn.db.domain.ice.DishFlavor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: 王海虹
 * @create: 2022-11-21 14:32
 */
@Data
public class DishDto extends Dish {
    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;

}
