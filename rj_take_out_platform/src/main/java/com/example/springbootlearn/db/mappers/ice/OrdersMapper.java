package com.example.springbootlearn.db.mappers.ice;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.example.springbootlearn.db.domain.ice.Orders;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author TCLDUSER
* @description 针对表【orders(订单表)】的数据库操作Mapper
* @createDate 2022-11-07 11:16:11
* @Entity com.example.springbootlearn.db.domain.ice.Orders
*/
@Mapper
@DS("ice")
public interface OrdersMapper extends BaseMapper<Orders> {

}




