package cn.itcast.order.mapper;

import cn.itcast.order.pojo.TbOrder;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 王海虹
* @description 针对表【tb_order】的数据库操作Mapper
* @createDate 2023-08-23 13:52:23
* @Entity cn.itcast.order.pojo.TbOrder
*/
@Mapper
@DS("cloud")
public interface TbOrderMapper extends BaseMapper<TbOrder> {

}




