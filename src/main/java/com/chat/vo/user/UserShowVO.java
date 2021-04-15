package com.chat.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


@ApiModel
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserShowVO {
    /**
     * 用户id
     */
    Long uid;
    /**
     * 昵称
     */
    String username = "";
    String nickname = "";

    /**
     * 年龄
     */
    Integer age = 0;
    /**
     * 性别。1=男，2=女，0=其他
     */
    Integer gender = 0;

    //头像图片地址
    String avatar = "";

    @ApiModelProperty("被关注数")
    Integer followedCnt = 0;

    @ApiModelProperty("关注别人数")
    Integer favorCnt = 0;

    @ApiModelProperty("我是否关注了此用户.0=未关注，1=已关注")
    Integer favored = 0;

}
