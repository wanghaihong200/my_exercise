package com.example.springbootlearn.model.dto.app;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: 王海虹
 * @create: 2022-09-23 16:27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JedisClusterHostAndPort {
    private String host;

    private String port;
}
