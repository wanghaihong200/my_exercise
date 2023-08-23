package com.example.springbootlearn.db.service.impl;

import com.example.springbootlearn.db.domain.ice.User;
import com.example.springbootlearn.db.mappers.ice.Usermapper;
import com.example.springbootlearn.db.service.UserService;
import com.example.springbootlearn.model.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author www.javacoder.top
 * @since 2022-10-13
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private Usermapper usermapper;

    /**
     * @param
     * @return
     */
    @Override
    public User selectUser(int id) {

        return usermapper.selectById(id);
    }

    /**
     * @return
     */
    @Override
    public Result selectUserAll() {
        System.out.println(usermapper.selectList(null));

        return Result.success();
    }
}
