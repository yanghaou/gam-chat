package com.chat.common;

import lombok.Data;

/**
 * 结果异常，会被 ExceptionHandler 捕捉并返回给前端
 *
 */

@Data
public class ResultException extends RuntimeException {

    private int code;
    private String status;

    public ResultException(String message) {
        super(message);
        this.status = message;
        this.code = 1001;
    }



    public ResultException(ResultCodeEnum error) {
        this.status = error.getMessage();
        this.code = error.getCode();
    }


}
