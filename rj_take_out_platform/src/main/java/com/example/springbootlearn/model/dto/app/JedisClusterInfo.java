package com.example.springbootlearn.model.dto.app;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description: 配置文件里 redis 相关信息的 实体类
 * @author: 王海虹
 * @create: 2022-09-23 16:25
 */
@Data
@Component
@ConfigurationProperties(prefix = "jedis")
public class JedisClusterInfo {
    private List<JedisClusterHostAndPort> nameList;

    private List<JedisClusterHostAndPort> galleryFactor;

    private List<JedisClusterHostAndPort> galleryGather;

    private List<JedisClusterHostAndPort> galleryReceive;

    private List<JedisClusterHostAndPort> bjRedis;
}
