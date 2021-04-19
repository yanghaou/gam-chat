package com.chat.service;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.chat.util.TLSSigAPIv2;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class NoticeService {
    @Autowired
    private TLSSigAPIv2 tlsSigAPIv2;
    @Value("${im.toBatch}")
    private String toBatchPath;
    @Value("${im.admin}")
    private String  imAdmin;
    @Value("${im.expire}")
    private long imExpireTime;
    @Value("${im.sdkappid}")
    private long sdkappid;
    @Value("${im.key}")
    private String key;


    /**
     * {
     *   "SyncOtherMachine": 2, // 消息不同步至发送方
     *   "To_Account": [ // 目标帐号列表
     *       "bonnie",
     *       "rong"
     *   ],
     *   "MsgRandom": 19901224, // 消息随机数
     *   "MsgBody": [ // 消息
     *       {
     *           "MsgType": "TIMTextElem", // 消息类型，TIMTextElem为文本消息
     *           "MsgContent": {
     *               "Text": "hi, beauty" // 消息文本
     *           }
     *       }
     *   ]
     * }
     *
     * 管理员批量给用户发单聊消息
     *
     */
    public int adminSendVerifyNotice(List toAccount, Object msg){
        String sig = tlsSigAPIv2.genUserSig(imAdmin, imExpireTime);
        StringBuilder url = new StringBuilder().append(toBatchPath).append("?")
                .append("sdkappid=").append(sdkappid).append("&")
                .append("random=").append(RandomUtil.randomNumber()).append("&")
                .append("identifier=").append(imAdmin).append("&")
                .append("usersig=").append(sig).append("&")
                .append("contenttype=json");

        Map m = Maps.newHashMap();
        m.put("SyncOtherMachine",2);
        m.put("To_Account",toAccount);
        m.put("MsgRandom",RandomUtil.randomNumber());
        m.put("MsgBody",msg);
        try {
            String response = HttpUtil.post(url.toString(),JSONObject.toJSONString(m),5000);
            if (response != null) {
                JSONObject jsonObject = JSONObject.parseObject(response);
                String status = jsonObject.getString("ActionStatus");
                if ("OK".equals(status)){
                    log.info("管理员审核完推送消息成功");
                }else if ("FAIL".equals(status)){
                    log.error("管理员审核完推送消息全部失败,推送结果:{}",jsonObject);
                }else {
                    log.error("管理员审核完推送消息部分失败,推送结果:{}",jsonObject);
                }
            }
        }catch (Exception e){
            log.error("管理员审核完推送消息异常",e);
        }

        return 1;
    }

}
