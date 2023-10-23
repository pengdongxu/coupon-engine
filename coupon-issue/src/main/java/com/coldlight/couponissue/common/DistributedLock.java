package com.coldlight.couponissue.common;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * @author pengpdx@163.com
 * @version 1.0
 * @description 分布式锁
 * @date 2023/10/21 11:37 AM
 */
@Component
@RequiredArgsConstructor
public class DistributedLock {

    private final StringRedisTemplate redisTemplate;

    /**
     * 获取锁
     *
     * @param lockKey    key
     * @param requestId  唯一标识
     * @param expireTime 超时时间
     * @return
     */
    public boolean acquireLock(String lockKey, String requestId, int expireTime) {
        Boolean locked = redisTemplate.opsForValue().setIfAbsent(lockKey, requestId, Duration.ofSeconds(expireTime));
        return locked != null && locked;
    }

    /**
     * 释放锁
     *
     * @param lockKey   key
     * @param requestId 唯一标识
     */
    public void releaseLock(String lockKey, String requestId) {
        String currentRequestId = redisTemplate.opsForValue().get(lockKey);
        if (requestId.equals(currentRequestId)) {
            redisTemplate.delete(lockKey);
        }
    }
}
