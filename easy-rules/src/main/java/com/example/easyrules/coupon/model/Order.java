package com.example.easyrules.coupon.model;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author pengpdx@163.com
 * @version 1.0
 * @description 订单实体
 * @date 2023/9/27 3:41 PM
 */
@Data
public class Order implements Serializable {
    @Serial
    private static final long serialVersionUID = 3915667010477847114L;

    private String id;

    private List<Project> projects;

    private BigDecimal amount;
}
