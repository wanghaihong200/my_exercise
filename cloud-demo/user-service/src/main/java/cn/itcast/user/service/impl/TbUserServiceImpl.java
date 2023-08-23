package cn.itcast.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.itcast.user.pojo.TbUser;
import cn.itcast.user.service.TbUserService;
import cn.itcast.user.mapper.TbUserMapper;
import org.springframework.stereotype.Service;

/**
* @author 王海虹
* @description 针对表【tb_user】的数据库操作Service实现
* @createDate 2023-08-23 15:09:28
*/
@Service
public class TbUserServiceImpl extends ServiceImpl<TbUserMapper, TbUser>
    implements TbUserService{

}




