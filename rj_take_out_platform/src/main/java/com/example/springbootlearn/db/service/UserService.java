package com.example.springbootlearn.db.service;

import com.example.springbootlearn.db.domain.ice.User;
import com.example.springbootlearn.model.Result;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author www.javacoder.top
 * @since 2022-10-13
 */

public interface UserService {
    public User selectUser(int id);

    public Result selectUserAll();
}
