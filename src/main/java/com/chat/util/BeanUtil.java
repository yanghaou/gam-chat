package com.chat.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;

@Slf4j
public class BeanUtil {

    public static void copyProperties(Object dest, Object orig){
        try {
            BeanUtils.copyProperties(dest, orig);
        } catch (IllegalAccessException | InvocationTargetException e) {
            log.error("BeanUtil error",e);
        }
    }
}
