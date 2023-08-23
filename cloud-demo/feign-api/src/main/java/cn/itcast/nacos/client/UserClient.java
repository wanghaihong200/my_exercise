package cn.itcast.nacos.client;

import cn.itcast.nacos.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @description:
 * @author: 王海虹
 * @create: 2023-08-07 16:37
 */
@FeignClient(name ="userservice",
        fallback = UserClientFallback.class
)
public interface UserClient {
    @GetMapping(path="/user/{id}")
    User findById(@PathVariable("id") Long id);
}
