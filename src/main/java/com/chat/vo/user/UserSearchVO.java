package com.chat.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@ApiModel
@Data
public class UserSearchVO {
    @ApiModelProperty("分页")
    @NotNull(message = "page can't be null")
    Integer page;
    @ApiModelProperty("每页大小")
    @NotNull(message = "pageSize can't be null")
    Integer pageSize;

    /**
     * 性别。1=男，2=女
     */
    @ApiModelProperty("性别.0=其他，1=男，2=女")
    Integer gender = 0;
    /**
     * 昵称
     */
    String username = "";
    /**
     * 年龄
     */
    @ApiModelProperty("年龄范围的最小值")
    Integer ageMin = 0;
    @ApiModelProperty("年龄范围的最大值")
    Integer ageMax = 0;

    //aboutMe
    String aboutMe = "";
    //aboutMatch
    String aboutMatch = "";
    String signature = "";

}
