package com.chat.util;


import cn.hutool.core.lang.Validator;
import com.chat.common.ResultException;
import org.apache.commons.lang3.StringUtils;

import static com.chat.common.ResultCodeEnum.*;

public class StringUtil {
    public static final String PW_PATTERN = "^(?![A-Za-z0-9]+$)(?![a-z0-9\\W]+$)(?![A-Za-z\\W]+$)(?![A-Z0-9\\W]+$)[a-zA-Z0-9\\W]{8,16}$";

    public static boolean checkPassword(String password){
//        if (!password.matches(PW_PATTERN)){
////            throw new ResultException(ResultCodeEnum.PASSWORD_INVALID);
//            throw new ResultException("password must contains uppercase letters, lowercase letters, numbers, special symbols and length must between 8,16");
//        }
        if (password == null || password.length()<8 || password.length()>16){
            throw new ResultException(PASSWORD_LEN_ERROR_EX);
        }

        return true;
    }

    public static boolean checkEmail(String email){
        if (!Validator.isEmail(email)){
//            throw new ResultException(USERNAME_INVALID);
            throw new ResultException(EMAIL_FORMAT_ERROR_EX);
        }

        return true;
    }

    public static boolean checkUsername(String username){
        if (StringUtils.isEmpty(username) || username.length() > 50){
//            throw new ResultException(NICKNAME_INVALID);
            throw new ResultException(USERNAME_FORMAT_ERROR_EX);
        }

        return true;
    }
}
