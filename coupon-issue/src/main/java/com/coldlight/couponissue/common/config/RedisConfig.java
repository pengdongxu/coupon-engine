package com.coldlight.couponissue.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

/**
 * @author pengpdx@163.com
 * @version 1.0
 * @description TODO
 * @date 2023/10/23 3:50 PM
 */
@Configuration
public class RedisConfig {

    @Bean
    public DefaultRedisScript<Long> redisScript() {
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("claim_coupon.lua")));
        redisScript.setResultType(Long.class);
        return redisScript;
    }
}
