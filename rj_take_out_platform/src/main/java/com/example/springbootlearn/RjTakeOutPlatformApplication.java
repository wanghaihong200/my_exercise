package com.example.springbootlearn;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisReactiveAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 瑞吉外卖练手项目
 *
 * @date: 2022/10/25
 */

@SpringBootApplication
@MapperScan("com.example.springbootlearn.db.mappers")
@EnableTransactionManagement
public class RjTakeOutPlatformApplication {
    private static Logger logger = LoggerFactory.getLogger(RjTakeOutPlatformApplication.class);

    public static void main(String[] args) {

        SpringApplication.run(RjTakeOutPlatformApplication.class, args);
        logger.info("瑞吉外卖应用启动开始。。。。");
    }
}
