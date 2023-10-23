package com.coldlight.couponissue.service;

/**
 * @author pengpdx@163.com
 * @version 1.0
 * @description 优惠券服务
 * @date 2023/10/21 11:44 AM
 */
public interface CouponService {

    /**
     * 加载优惠券
     *
     * @param couponId 优惠券ID
     * @param stock    库存量
     */
    public void loadCouponData(String couponId, String stock);

    /**
     * 领取优惠券
     *
     * @param userId   用户ID
     * @param couponId 优惠券ID
     */
    void claimCoupon(String userId, String couponId);
}
