package com.chat.vo.user;

import com.chat.entity.auth.UserRole;
import com.chat.entity.user.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;


@ApiModel
@Data
@Accessors(chain = true)
public class UserDetailVO {
    @ApiModelProperty("用户账号信息信息。只做展示，不用与修改")
    UserAccount userAccount;

    @ApiModelProperty("用户基本信息")
    UserBase userBase;
    @ApiModelProperty("用户位置信息")
    UserLocation userLocation;

    @ApiModelProperty("用户扩展信息")
    UserExtra userExtra;

    @ApiModelProperty("用户图片")
    List<UserPic> userPics;

//    @ApiModelProperty("修改用户哪部分信息，对应的参数必填。1=修改基本信息和位置信息，2=用户扩展信息，3=用户图片")
//    Integer step;

    @ApiModelProperty("腾讯IM和七牛云域名等其他信息,只用来展示,更新用户信息是不传")
    UserOther other;

    @ApiModelProperty("用户拥有的角色权限")
    List<UserRole> userRoles;
}
