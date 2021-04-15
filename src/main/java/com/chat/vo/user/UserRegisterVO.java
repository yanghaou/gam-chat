package com.chat.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@ApiModel
@Data
public class UserRegisterVO {
    @ApiModelProperty("邮箱。必传")
    @NotEmpty(message = "email can't be empty")
    private String email = "";

    @ApiModelProperty("用户名。必传")
    @NotEmpty(message = "username can't be empty")
    private String username = "";

    @ApiModelProperty("密码。登录时必传")
    @NotEmpty(message = "password can't be empty")
    private String password = "";

}
