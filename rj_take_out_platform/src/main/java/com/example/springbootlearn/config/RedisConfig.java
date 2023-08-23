package com.example.springbootlearn.config;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: 王海虹
 * @create: 2022-12-14 19:38
 */
@Configuration
public class RedisConfig {
    @Autowired
    private Environment environment;

    /**
     * 配置lettuce连接池
     */
    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.redis.cluster.lettuce.pool")
    public GenericObjectPoolConfig redisPool() {
        return new GenericObjectPoolConfig();
    }

    /**
     * 配置第一个数据源
     */
    @Bean("redisClusterConfig")
    @Primary
    public RedisClusterConfiguration redisClusterConfig() {

        Map<String, Object> source = new HashMap<>(8);
        source.put("spring.redis.cluster.nodes", environment.getProperty("spring.redis.cluster.nodes"));
        source.put("spring.redis.cluster.max-redirects", environment.getProperty("spring.redis.cluster.max-redirects"));
        RedisClusterConfiguration redisClusterConfiguration;
        redisClusterConfiguration = new RedisClusterConfiguration(new MapPropertySource("RedisClusterConfiguration",
                source));
        return redisClusterConfiguration;
    }

    /**
     * 配置第一个数据源的连接工厂
     * 这里注意：需要添加@Primary 指定bean的名称，目的是为了创建两个不同名称的LettuceConnectionFactory
     */
    @Bean("lettuceConnectionFactory")
    @Primary
    public LettuceConnectionFactory lettuceConnectionFactory(GenericObjectPoolConfig redisPool, @Qualifier(
            "redisClusterConfig") RedisClusterConfiguration redisClusterConfig) {
        LettuceClientConfiguration clientConfiguration =
                LettucePoolingClientConfiguration.builder().poolConfig(redisPool).build();
        return new LettuceConnectionFactory(redisClusterConfig, clientConfiguration);
    }

    /**
     * 配置第一个数据源的RedisTemplate
     * 注意：这里指定使用名称=factory 的 RedisConnectionFactory
     * 并且标识第一个数据源是默认数据源 @Primary
     */
    @Bean("bjRedisClusterTemplate")
    @Primary
    public RedisTemplate redisTemplate(@Qualifier("lettuceConnectionFactory") RedisConnectionFactory redisConnectionFactory) {
        return getRedisTemplate(redisConnectionFactory);
    }

    @Bean("bjRedisClusterStringTemplate")
    @Primary
    public StringRedisTemplate bjRedisClusterStringTemplate(@Qualifier("lettuceConnectionFactory") RedisConnectionFactory redisConnectionFactory) {
        return getStringRedisTemplate(redisConnectionFactory);
    }

    /**
     * 配置第二个数据源
     */
    @Bean("galleryFactorRedisClusterConfig")
    public RedisClusterConfiguration galleryFactorRedisConfig() {

        Map<String, Object> source = new HashMap<>(8);
        source.put("spring.redis.cluster.nodes", environment.getProperty("spring.galleryFactor.cluster.nodes"));
        source.put("spring.redis.cluster.max-redirects", environment.getProperty("spring.galleryFactor.cluster.max-redirects"));
        RedisClusterConfiguration redisClusterConfiguration;
        redisClusterConfiguration = new RedisClusterConfiguration(new MapPropertySource("RedisClusterConfiguration",
                source));

        return redisClusterConfiguration;
    }

    @Bean("galleryFactorLettuceConnectionFactory")
    public LettuceConnectionFactory secondaryLettuceConnectionFactory(GenericObjectPoolConfig redisPool, @Qualifier(
            "galleryFactorRedisClusterConfig") RedisClusterConfiguration galleryFactorRedisConfig) {
        LettuceClientConfiguration clientConfiguration =
                LettucePoolingClientConfiguration.builder().poolConfig(redisPool).build();
        return new LettuceConnectionFactory(galleryFactorRedisConfig, clientConfiguration);
    }

    /**
     * 获取第二个数据源的RedisTemplate
     * 注意：这里指定使用名称=factory2 的 RedisConnectionFactory
     */
    @Bean("galleryFactorRedisTemplate")
    public RedisTemplate galleryFactorRedisTemplate(@Qualifier("galleryFactorLettuceConnectionFactory") RedisConnectionFactory redisConnectionFactory) {
        return getRedisTemplate(redisConnectionFactory);
    }

    @Bean("galleryFactorStringRedisTemplate")
    public StringRedisTemplate galleryFactorStringRedisTemplate(@Qualifier("galleryFactorLettuceConnectionFactory") RedisConnectionFactory redisConnectionFactory) {
        return getStringRedisTemplate(redisConnectionFactory);
    }

    /*** 功能描述: 根据连接工厂获取一个RedisTemplate
     */
    private RedisTemplate getRedisTemplate(RedisConnectionFactory factory) {
        //RedisTemplate<String, Object> template = new RedisTemplate<>();
        //template.setConnectionFactory(factory);
        //
        //Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        //ObjectMapper om = new ObjectMapper();
        //om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        //om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        //jackson2JsonRedisSerializer.setObjectMapper(om);
        //
        //StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        //// key采用String的序列化方式
        //template.setKeySerializer(stringRedisSerializer);
        //// hash的key也采用String的序列化方式
        //template.setHashKeySerializer(stringRedisSerializer);
        //// value序列化方式采用jackson
        //template.setValueSerializer(jackson2JsonRedisSerializer);
        //// hash的value序列化方式采用jackson
        //template.setHashValueSerializer(jackson2JsonRedisSerializer);
        //template.afterPropertiesSet();

        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        //key值使用spring默认的StringRedisSerializer
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //value值使用fastjson的GenericFastJsonRedisSerializer
        GenericFastJsonRedisSerializer fastJsonRedisSerializer = new GenericFastJsonRedisSerializer();
        redisTemplate.setValueSerializer(fastJsonRedisSerializer);
        //以下是hash序列化的配置
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(fastJsonRedisSerializer);

        return redisTemplate;
    }

    /**
    * 根据连接工厂获取一个StringRedisTemplate，StringRedisSerializer：Key或者value为字符串的场景，
     * 根据指定的charset对数据的字节序列编码成string，是“new String(bytes, charset)”和“string.getBytes(charset)”的直接封装。
     * 是最轻量级和高效的策略。
    * @param:
    * @return:
    * @date: 2022/12/15
    */
    private StringRedisTemplate getStringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(redisConnectionFactory);
        return stringRedisTemplate;
    }
}
