package com.coldlight.couponissue;

import com.coldlight.couponissue.common.exception.ErrorResponse;
import com.coldlight.couponissue.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author pengpdx@163.com
 * @version 1.0
 * @description 优惠券
 * @date 2023/10/20 4:47 PM
 */
@RestController
@RequestMapping("/coupon")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    /**
     * 预加载近Redis
     */
    @GetMapping("/loadCouponData")
    public ErrorResponse loadCouponData() {
        String couponId = UUID.randomUUID().toString().replace("-", "");
        couponService.loadCouponData(couponId, "50");
        return ErrorResponse.success(couponId);
    }


    @GetMapping("/claimCoupon")
    public void claimCoupon(@RequestParam String userId, @RequestParam String couponId) {
        couponService.claimCoupon(userId, couponId);
    }

}
