package com.coldlight.couponissue.common;

/**
 * @author pengpdx@163.com
 * @version 1.0
 * @description 优惠券领取状态
 * @date 2023/10/22 10:26 PM
 */
public class CouponStatus {
    public static final int SUCCESS = 1;
    public static final int OUT_OF_STOCK = 0;
    public static final int ALREADY_CLAIMED = -1;
    public static final int COUPON_NOT_FOUND = -2;
}
