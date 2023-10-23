package com.coldlight.couponissue.service;

import com.coldlight.couponissue.common.DistributedLock;
import com.coldlight.couponissue.common.LuaScriptExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.coldlight.couponissue.common.CouponStatus.*;

/**
 * @author pengpdx@163.com
 * @version 1.0
 * @description 优惠券服务
 * @date 2023/10/21 11:44 AM
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

    private final DistributedLock distributedLock;
    private final LuaScriptExecutor luaScript;
    private final StringRedisTemplate stringRedisTemplate;

    private static final String COUPON_KEY = "coupon:";

    /**
     * 加载优惠券到Redis
     */
    @Override
    public void loadCouponData(String couponId, String stock) {
        // Define the key for the coupon
        String couponKey = COUPON_KEY + couponId;
        String stockKey = "stock";           // 库存属性名
        String stockValue = "50";                // 库存数量

        stringRedisTemplate.opsForHash().put(couponKey, stockKey, stockValue);
    }

    @Override
    public void claimCoupon(String userId, String couponId) {
        String lockKey = "coupon_lock:" + couponId;
        String requestId = UUID.randomUUID().toString().replace("-", "");
        int maxRetries = 30;

        try {
            boolean lockAcquired = false;
            for (int retry = 0; retry < maxRetries; retry++) {
                lockAcquired = distributedLock.acquireLock(lockKey, requestId, 30);
                if (lockAcquired) {
                    break;
                } else {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
            //扣除库存
            if (lockAcquired) {
                processCouponClaim(userId, couponId);
            }
        } finally {
            distributedLock.releaseLock(lockKey, requestId);
        }

    }

    public void processCouponClaim(String userId, String couponId) {
        List<String> keys = Arrays.asList(COUPON_KEY + couponId);
        String[] args = {userId}; // 将参数作为字符串数组
        Long result = luaScript.executeLuaScript(keys, args);

        if (result != null) {
            switch (result.intValue()) {
                case SUCCESS:
                    // 成功领取，执行相应的业务逻辑
                    handleSuccessfulClaim(userId, couponId);
                    break;
                case OUT_OF_STOCK:
                    // 库存不足，发送库存不足通知
                    handleOutOfStock(userId, couponId);
                    break;
                case ALREADY_CLAIMED:
                    // 已领取，发送已领取通知
                    handleAlreadyClaimed(userId, couponId);
                    break;
                case COUPON_NOT_FOUND:
                    // 优惠券不存在，处理不存在的情况
                    handleCouponNotFound(userId, couponId);
                    break;
                default:
                    // 其他状态，处理其他情况
                    handleOtherStatus(userId, couponId, result);
                    break;
            }
        }
    }

    private void handleSuccessfulClaim(String userId, String couponId) {
        // 处理成功领取逻辑
        //Save coupon information to MySQL and send a Kafka message
       /* Coupon coupon = new Coupon();
        coupon.setUserId(userId);
        coupon.setCouponId(couponId);
        couponService.saveCoupon(coupon);
        kafkaProducer.sendCouponClaimedEvent("Coupon claimed: " + couponId);*/
        log.info("\n用户：{}, 领取了优惠券。。。。", userId);
    }

    private void handleOutOfStock(String userId, String couponId) {
        // 处理库存不足逻辑
        log.info("\n用户：{}, 领取优惠券,库存不足。。。。", userId);
    }

    private void handleAlreadyClaimed(String userId, String couponId) {
        // 处理已领取逻辑
        log.info("\n用户：{}, 已经领取过优惠券,不能重复领取。。。。", userId);
    }

    private void handleCouponNotFound(String userId, String couponId) {
        // 处理优惠券不存在逻辑
        log.info("\n用户：{}, 优惠券不存在,请联系管理员。。。。", userId);
    }

    private void handleOtherStatus(String userId, String couponId, Long status) {
        // 处理其他状态的逻辑
        log.info("\n用户：{}, 发生了其他异常。。。。", userId);
    }

}
