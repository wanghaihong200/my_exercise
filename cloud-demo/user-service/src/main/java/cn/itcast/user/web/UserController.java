package cn.itcast.user.web;

import cn.hutool.core.date.DateUtil;
import cn.itcast.user.config.UserServiceConfig;
import cn.itcast.user.pojo.TbUser;
import cn.itcast.user.service.TbUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;


import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private TbUserService tbUserService;

    @Autowired
    private UserServiceConfig userServiceConfig;

    /**
     * 路径： /user/110
     *
     * @param id 用户id
     * @return 用户
     */
    @GetMapping("/{id}")
    public TbUser queryById(@PathVariable("id") Long id) {
        return tbUserService.getById(id);
    }

    @GetMapping("/now")
    public String now() {
        //return "now: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern(dateformat));
        return "now: " + DateUtil.format(new Date(), userServiceConfig.getDateformat())+"\n 中文日期："+DateUtil.format(new Date(), userServiceConfig.getDateformatChinese());
    }
}
