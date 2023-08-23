package com.example.springbootlearn;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springbootlearn.db.mappers.ice.Usermapper;
import com.example.springbootlearn.db.domain.ice.User;
import com.example.springbootlearn.model.dto.app.JedisClusterInfo;

import io.lettuce.core.codec.CRC16;
import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@Slf4j
class RjTakeOutPlatformApplicationTests {
    @Autowired
    private Usermapper userMapper;


    @Test
    void testGetAll() {
        List<User> userList = userMapper.selectList(null);
        System.out.println(userList);
    }

    @Test
    void testSave() {
        User user = new User();
        user.setName("神龟--维斯布鲁克");
        user.setPhone("13391387841");
        userMapper.insert(user);
        System.out.println(user);
    }

    @Test
    void testGetById() {
        User user = userMapper.selectById(1L);
        System.out.println(user);
    }

    @Test
    void testUpdate() {
        User user = User.builder()
                .id(1L)
                .name("斯托克顿")
                .build();
        userMapper.updateById(user);
    }

    @Test
    void testSelectByMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "科比");
        map.put("age", 24);
        List<User> listUser = userMapper.selectByMap(map);
        listUser.forEach(t -> System.out.println(t.getName() + "," + t.getPhone()));
    }

    //@Test
    //void testSelectPage() {
    //    Page<User> page = new Page<>(1, 3);
    //    User user = User.builder()
    //            .build();
    //    IPage<User> list = userMapper.selectUser(user, page);
    //    if (!CollectionUtils.isEmpty(list.getRecords())) {
    //        list.getRecords().forEach(System.out::println);
    //    }
    //}

    @Test
    void testWrapper() {
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.lt("age", "20")
                .gt("age", 10)
                .or()
                .eq("name", "科比")
                .orderByDesc("age");
        List<User> userList = userMapper.selectList(qw);
        userList.forEach(System.out::println);
    }

    @Autowired
    @Qualifier("bjRedisClusterTemplate")
    private RedisTemplate<String, Object> bjRedisClusterTemplate;

    @Autowired
    @Qualifier("bjRedisClusterStringTemplate")
    private StringRedisTemplate bjRedisClusterStringTemplate;


    @Autowired
    @Qualifier ("galleryFactorRedisTemplate")
    RedisTemplate<String, Object> galleryFactorRedisTemplate;

    @Autowired
    @Qualifier("galleryFactorStringRedisTemplate")
    StringRedisTemplate galleryFactorStringRedisTemplate;

    @Autowired
    private Environment environment;

    @Test
    public void testEnvironment() {
        System.out.println("开始测试");
        String property = environment.getProperty("spring.secondaryRedis.cluster.nodes");
        System.out.println("property = " + property);
        System.out.println("over");
    }

    // 同时设置两个数据源的数据
    @Test
    public void testTemplateSet() {
        System.out.println("同时设置数据");
        Map<String,String> map = new HashMap<>();
        map.put("json_key","json_value");
        JSONObject jsonObject = new JSONObject();
        jsonObject.putAll(map);
        System.out.println(jsonObject);
        bjRedisClusterTemplate.opsForValue().set("redis-key3", 1);
        galleryFactorRedisTemplate.opsForValue().set("redis-key3", 2);

        bjRedisClusterStringTemplate.opsForValue().set("redis-keyString", jsonObject.toJSONString());
        galleryFactorStringRedisTemplate.opsForValue().set("redis-keyString", jsonObject.toJSONString());
        System.out.println("over");
    }

    // 同时获取两个数据源的数据
    @Test
    public void testTemplateGet() {
        System.out.println("同时获取数据");
        Object key1 = bjRedisClusterTemplate.opsForValue().get("redis-key2");
        Object key2 = galleryFactorRedisTemplate.opsForValue().get("redis-key2");
        System.out.println("key1 = " + key1);
        System.out.println("key2 = " + key2);
        System.out.println("over");
    }
}
