package com.chat.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@ApiModel
@Data
@AllArgsConstructor
public class UserLoginResVO {
    @ApiModelProperty("uid")
    private Long uid;

    @ApiModelProperty("token")
    private String token;

    @ApiModelProperty("accountStatus账号状态")
    private Integer accountStatus;

}
