package com.chat.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserShowDTO {
    /**
     * 用户id
     */
    Long id;
    String email;
    /**
     * 昵称
     */
    String username = "";
    /**
     * 年龄
     */
    Integer age = 0;

    String signature = "";
    /**
     * 性别。1=男，2=女，0=其他
     */
    Integer gender = 0;

    //头像图片地址
    String avatar = "";
}
