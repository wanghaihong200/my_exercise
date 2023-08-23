package com.example.springbootlearn.db.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springbootlearn.db.domain.ice.OrderDetail;
import com.example.springbootlearn.db.service.OrderDetailService;
import com.example.springbootlearn.db.mappers.ice.OrderDetailMapper;
import org.springframework.stereotype.Service;

/**
* @author TCLDUSER
* @description 针对表【order_detail(订单明细表)】的数据库操作Service实现
* @createDate 2022-11-07 11:16:05
*/
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail>
    implements OrderDetailService{

}




