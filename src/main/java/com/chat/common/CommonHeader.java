package com.chat.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommonHeader {
    Long uid;
    String appType;
    Integer clientType;
    String appVersion;
    String language;
    String device;
    String deviceVersion;

}
