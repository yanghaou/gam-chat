package com.chat.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

@Slf4j
@Component
@Aspect
@EnableAspectJAutoProxy
public class AopConfig {

    /**
     * 拦截接口请求，打印IP和请求时间等信息
     */
    @Around("@annotation(org.springframework.web.bind.annotation.RestController)")
    private Object printRequestDatagram(ProceedingJoinPoint joinPoint) throws Throwable {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ip = getIpAddress(request);
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        SimpleDateFormat dateFormat = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
        long req = System.currentTimeMillis();
        String reqTime = dateFormat.format(new Date(req));
        Object[] args = joinPoint.getArgs ();
        Long userId = Long.valueOf(request.getIntHeader("UserId"));

        log.info ("拦截到请求===>请求编号:{},请求者:{},请求者IP:{},请求时间:{},请求接口:{},请求方法:{},参数内容:{}",
                userId+"-"+req,userId,ip,reqTime,request.getMethod() + " " + request.getRequestURL(),method.getName(),Arrays.toString(args));

        Object result = joinPoint.proceed ();

        long respTime = System.currentTimeMillis() - req;
        String d = String.valueOf(respTime);
        log.info("返回请求结果===>请求编号:{},请求耗时:{}秒,result:{}",userId+"-"+req,Double.parseDouble(d)/1000,result);

        return result;
    }

    private String getIpAddress(HttpServletRequest request){
        final String UNKNOWN = "unknown";
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
