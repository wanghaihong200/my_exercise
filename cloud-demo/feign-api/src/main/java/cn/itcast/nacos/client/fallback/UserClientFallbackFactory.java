package cn.itcast.nacos.client.fallback;

import cn.itcast.nacos.client.UserClient;
import cn.itcast.nacos.pojo.TbUser;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: 王海虹
 * @create: 2023-08-07 17:22
 */

@Component
@Slf4j
public class UserClientFallbackFactory implements FallbackFactory<UserClient> {


    @Override
    public UserClient create(Throwable cause) {
        return new UserClient() {
            @Override
            public TbUser queryById(Long id) {
                log.error("查询用户异常", cause);
                return TbUser.builder().
                        id(10L).
                        username("默认用户").
                        address("默认地址").
                        build();
            }
        };
    }
}
