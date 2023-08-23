package com.example.springbootlearn.db.mappers.ice;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.example.springbootlearn.db.domain.ice.ShoppingCart;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author TCLDUSER
* @description 针对表【shopping_cart(购物车)】的数据库操作Mapper
* @createDate 2022-11-07 11:16:28
* @Entity com.example.springbootlearn.db.domain.ice.ShoppingCart
*/
@Mapper
@DS("ice")
public interface ShoppingCartMapper extends BaseMapper<ShoppingCart> {

}




