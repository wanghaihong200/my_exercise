package com.example.springbootlearn.db.service;

import com.example.springbootlearn.db.domain.ice.Employee;

/**
 * @description:
 * @author: 王海虹
 * @create: 2022-11-29 20:41
 */
public interface TokenService {
    public String getToken(Employee user);
}
