package com.coldlight.couponissue.common.exception;

/**
 * @author pengpdx@163.com
 * @version 1.0
 * @description 优惠券异常
 * @date 2023/10/23 10:44 AM
 */
public class CouponException extends RuntimeException{

    public CouponException(String message) {
        super(message);
    }
}
