package cn.itcast.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.itcast.order.pojo.TbOrder;
import cn.itcast.order.service.TbOrderService;
import cn.itcast.order.mapper.TbOrderMapper;
import org.springframework.stereotype.Service;

/**
* @author 王海虹
* @description 针对表【tb_order】的数据库操作Service实现
* @createDate 2023-08-23 13:52:23
*/
@Service
public class TbOrderServiceImpl extends ServiceImpl<TbOrderMapper, TbOrder>
    implements TbOrderService{

    @Override
    public TbOrder queryOrder(Long orderId) {
        return this.getById(orderId);
    }
}




