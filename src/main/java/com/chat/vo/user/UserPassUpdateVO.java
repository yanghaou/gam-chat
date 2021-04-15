package com.chat.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@ApiModel
@Data
public class UserPassUpdateVO {
    @ApiModelProperty("旧密码")
    @NotEmpty(message = "old password can't be empty")
    private String oldPassword = "";
    @ApiModelProperty("新密码")
    @NotEmpty(message = "new password can't be empty")
    private String newPassword = "";

}
