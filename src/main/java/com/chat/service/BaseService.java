package com.chat.service;


import com.chat.common.CommonHeader;
import com.chat.common.ResultException;
import com.chat.entity.user.UserAccount;
import com.chat.entity.user.UserBase;
import com.chat.repository.user.UserAccountRepository;
import com.chat.repository.user.UserBaseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import static com.chat.common.UserConstant.NOT_DEL;

/**
 * 每个请求接收公共参数
 */
@Service
@Slf4j
public class BaseService {
    @Autowired
    UserAccountRepository userAccountRepository;
    @Autowired
    UserBaseRepository userBaseRepository;

    public CommonHeader getCommonHeader() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
        String u = request.getHeader("Uid");
        Long uid = null;
        if (u != null && !u.equals("")){
            uid = Long.parseLong(u);
        }
        String appType = request.getHeader("AppType");
        Integer clientType = request.getIntHeader("ClientType");
        String device = request.getHeader("Device");
        String deviceVersion = request.getHeader("DeviceVersion");
        String appVersion = request.getHeader("AppVersion");
        String lan = request.getHeader("Language");

        return new CommonHeader(uid,appType, clientType,appVersion,lan,device,deviceVersion);
    }

    public Long getUid(){
        return getCommonHeader().getUid();
    }

    public UserAccount getUserByHeader(){
        CommonHeader header = getCommonHeader();
        UserAccount user = userAccountRepository.findByAppTypeAndClientTypeAndUidAndIsDel(header.getAppType(),header.getClientType(),header.getUid(),NOT_DEL);
        if (user == null) {
            log.error("user not exist,header:{}",header);
            throw new ResultException("user not exist");
        }

        return user;
    }

    public UserBase getUserBaseByHeader(){
        CommonHeader header = getCommonHeader();
        UserBase user = userBaseRepository.findByUidAndIsDel(header.getUid(),NOT_DEL);
        if (user == null) {
            log.error("user not exist,header:{}",header);
            throw new ResultException("user not exist");
        }

        return user;
    }




    public UserAccount getUserByCache(String appType, String clientType, String email){
        //先查缓存，没有再从mysql查

        return null;
    }
}
