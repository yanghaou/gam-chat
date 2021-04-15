package com.chat.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@ApiModel
@Data
public class UserLoginVO {
    @ApiModelProperty("邮箱。必传")
    @NotEmpty(message = "email can't be empty")
    private String email = "";

    @ApiModelProperty("密码。登录时必传")
    @NotEmpty(message = "password can't be empty")
    private String password = "";

}
