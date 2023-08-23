package cn.itcast.order.service;

import cn.itcast.order.pojo.TbOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

/**
* @author 王海虹
* @description 针对表【tb_order】的数据库操作Service
* @createDate 2023-08-23 13:52:23
*/
public interface TbOrderService extends IService<TbOrder> {
    public TbOrder queryOrder(@Param("id") Long orderId);
}
