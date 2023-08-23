package cn.itcast.nacos.client;

import cn.itcast.nacos.pojo.User;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: 王海虹
 * @create: 2023-08-07 17:22
 */
@Component
public class UserClientFallback implements UserClient {
    @Override
    public User findById(Long id) {
        return User.builder().
                id(10L).
                username("默认用户").
                address("默认地址").
                build();
    }


}
