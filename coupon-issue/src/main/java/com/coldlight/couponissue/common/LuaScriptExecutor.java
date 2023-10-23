package com.coldlight.couponissue.common;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author pengpdx@163.com
 * @version 1.0
 * @description 脚本执行
 * @date 2023/10/21 11:41 AM
 */
@Component
@RequiredArgsConstructor
public class LuaScriptExecutor {

    private final StringRedisTemplate redisTemplate;
    private final DefaultRedisScript<Long> luaScript;

    /**
     * 脚本执行
     *
     * @param keys key
     * @param args 参数
     * @return
     */
    public Long executeLuaScript(List<String> keys, String... args) {
        return redisTemplate.execute(luaScript, keys, args);
    }
}
