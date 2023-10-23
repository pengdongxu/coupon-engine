package com.coldlight.couponissue;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @author pengpdx@163.com
 * @version 1.0
 * @description Redis字符串命令测试
 * @date 2023/10/20 9:00 PM
 */
@SpringBootTest
@Log4j2
public class RedisStringCommandTest {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    void test_set() {
        redisTemplate.opsForValue().set("name", "SpringBoot");
    }

    @Test
    void test_modify_ttl() {
        redisTemplate.opsForValue().getAndExpire("testkey", 10, TimeUnit.SECONDS);
    }

    @Test
    void test_get_and_set() {
        String key = redisTemplate.opsForValue().getAndSet("city", "wuhan");
        log.info("\n get key = {}", key);
    }
}
