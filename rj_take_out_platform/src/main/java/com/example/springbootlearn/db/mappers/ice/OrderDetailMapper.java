package com.example.springbootlearn.db.mappers.ice;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.example.springbootlearn.db.domain.ice.OrderDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author TCLDUSER
* @description 针对表【order_detail(订单明细表)】的数据库操作Mapper
* @createDate 2022-11-07 11:16:05
* @Entity com.example.springbootlearn.db.domain.ice.OrderDetail
*/
@Mapper
@DS("ice")
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {

}




