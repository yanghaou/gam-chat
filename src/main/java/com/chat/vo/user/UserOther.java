package com.chat.vo.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserOther {
    @ApiModelProperty("腾讯IM签名")
    String userSign;
    @ApiModelProperty("IM Id")
    String imId;
    @ApiModelProperty("七牛文件域名")
    String qiNiuDomain;
}