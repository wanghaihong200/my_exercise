package com.example.springbootlearn.db.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.springbootlearn.db.domain.ice.Employee;
import com.example.springbootlearn.db.service.TokenService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @description:
 * @author: 王海虹
 * @create: 2022-11-29 20:42
 */
@Service
public class TokenServiceImpl implements TokenService {

    /**
     * @param user
     * @return
     */
    @Override
    public String getToken(Employee user) {
        Date start = new Date();
        long expireTime = System.currentTimeMillis() + 60* 60 * 1000 * 48;//48小时有效时间
        Date end = new Date(expireTime);
        String token = "";

        token = JWT.create()
                .withAudience(user.getId().toString())
                .withIssuedAt(start)
                .withExpiresAt(end)
                .sign(Algorithm.HMAC256(user.getPassword()));
        return token;

    }
}
