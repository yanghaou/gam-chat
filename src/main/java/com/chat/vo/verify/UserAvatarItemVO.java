package com.chat.vo.verify;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel
@NoArgsConstructor
@AllArgsConstructor
public class UserAvatarItemVO {
    @ApiModelProperty("用户UID")
    Long uid;
    @ApiModelProperty("待审核的头像地址")
    String avatarTemp;

}
