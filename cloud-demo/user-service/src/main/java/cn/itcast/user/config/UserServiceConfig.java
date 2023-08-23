package cn.itcast.user.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: 王海虹
 * @create: 2023-08-07 11:50
 */
@Configuration
@Data
@RefreshScope
public class UserServiceConfig {
    @Value("${pattern.dateformat}")
    private String dateformat;

    @Value("${dateformat-chinese}")
    private String dateformatChinese;
}
