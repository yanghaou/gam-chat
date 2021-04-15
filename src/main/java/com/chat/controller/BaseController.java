package com.chat.controller;


import com.chat.common.ResultException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.stream.Collectors;

/**
 * 每个请求接收公共参数
 */
public abstract class BaseController {

    public void checkParam(BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            String error = bindingResult.getAllErrors().stream().map(ObjectError::getObjectName).collect(Collectors.joining(","));
//            throw new ResultException(ResultCodeEnum.PARAM_ERROR);
            throw new ResultException("param exception : "+error);
        }
    }
}
