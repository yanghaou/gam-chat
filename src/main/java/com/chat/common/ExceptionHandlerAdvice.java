package com.chat.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
@Slf4j
public class ExceptionHandlerAdvice {
    @ExceptionHandler(Exception.class)
    public CommonResult handleException(Exception e) {
        log.error("handleException:", e);
        return CommonResult.failed("server error");
    }

    @ExceptionHandler(NullPointerException.class)
    public CommonResult handleNullPointerException(NullPointerException e) {
        log.error("handleNullPointerException", e);
        return CommonResult.failed("NullPointer exception");
    }

    @ExceptionHandler(ResultException.class)
    public CommonResult handleResultException(ResultException e, HttpServletRequest request) {
        log.error("uri = {},error code = {}, error msg = {}", request.getRequestURI(), e.getCode(),e.getStatus());
        return CommonResult.failed(e.getCode(),e.getStatus());
    }
}
