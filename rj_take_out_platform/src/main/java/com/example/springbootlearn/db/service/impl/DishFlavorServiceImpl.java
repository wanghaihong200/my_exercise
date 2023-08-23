package com.example.springbootlearn.db.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springbootlearn.db.domain.ice.DishFlavor;
import com.example.springbootlearn.db.service.DishFlavorService;
import com.example.springbootlearn.db.mappers.ice.DishFlavorMapper;
import org.springframework.stereotype.Service;

/**
* @author TCLDUSER
* @description 针对表【dish_flavor(菜品口味关系表)】的数据库操作Service实现
* @createDate 2022-11-07 11:15:59
*/
@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor>
    implements DishFlavorService{

}




