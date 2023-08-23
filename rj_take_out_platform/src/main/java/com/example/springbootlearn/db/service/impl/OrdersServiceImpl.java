package com.example.springbootlearn.db.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springbootlearn.db.domain.ice.Orders;
import com.example.springbootlearn.db.service.OrdersService;
import com.example.springbootlearn.db.mappers.ice.OrdersMapper;
import org.springframework.stereotype.Service;

/**
* @author TCLDUSER
* @description 针对表【orders(订单表)】的数据库操作Service实现
* @createDate 2022-11-07 11:16:11
*/
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders>
    implements OrdersService{

}




