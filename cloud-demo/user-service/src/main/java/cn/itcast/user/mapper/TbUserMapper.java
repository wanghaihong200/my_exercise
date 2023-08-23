package cn.itcast.user.mapper;

import cn.itcast.user.pojo.TbUser;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 王海虹
* @description 针对表【tb_user】的数据库操作Mapper
* @createDate 2023-08-23 15:09:28
* @Entity cn.itcast.user.pojo.TbUser
*/
@Mapper
@DS("cloud")
public interface TbUserMapper extends BaseMapper<TbUser> {

}




