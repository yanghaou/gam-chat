package com.chat.chatparent;


import com.chat.entity.user.UserBase;
import com.chat.vo.user.UserDetailVO;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;

public class Test {
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        UserDetailVO userVO =new UserDetailVO();

        UserBase user = new UserBase();

        BeanUtils.copyProperties(user, userVO);

        System.out.println();
    }
}
