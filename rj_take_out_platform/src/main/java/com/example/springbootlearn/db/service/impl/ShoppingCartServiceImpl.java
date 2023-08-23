package com.example.springbootlearn.db.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springbootlearn.db.domain.ice.ShoppingCart;
import com.example.springbootlearn.db.service.ShoppingCartService;
import com.example.springbootlearn.db.mappers.ice.ShoppingCartMapper;
import org.springframework.stereotype.Service;

/**
* @author TCLDUSER
* @description 针对表【shopping_cart(购物车)】的数据库操作Service实现
* @createDate 2022-11-07 11:16:28
*/
@Service
@DS("ice")
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart>
    implements ShoppingCartService{

}




