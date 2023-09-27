package com.example.easyrules.coupon.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author pengpdx@163.com
 * @version 1.0
 * @description 订单项目信息
 * @date 2023/9/27 3:46 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project implements Serializable {
    @Serial
    private static final long serialVersionUID = -7958643554400180697L;

    private String name;

    private String code;

    private BigDecimal amount;
}
