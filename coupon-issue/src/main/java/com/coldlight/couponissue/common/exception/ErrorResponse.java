package com.coldlight.couponissue.common.exception;

import lombok.Data;

/**
 * @author pengpdx@163.com
 * @version 1.0
 * @description TODO
 * @date 2023/10/23 10:45 AM
 */
@Data
public class ErrorResponse {

    private String code;
    private String data;
    private String message;

    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ErrorResponse(String code, String data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public static ErrorResponse success(String data) {
        return new ErrorResponse("0", data, "");
    }

}


