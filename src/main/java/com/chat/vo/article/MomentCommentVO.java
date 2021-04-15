package com.chat.vo.article;

import com.chat.vo.user.UserShowVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;

/**
 * 文章点赞评论回复
 */
@Data
public class MomentCommentVO {

    @ApiModelProperty("id")
    Long id;

    @ApiModelProperty("几楼")
    Integer level;

    @ApiModelProperty("评论用户信息")
    UserShowVO user;

    /**
     * 对文章的操作类型,1=评论,2=回复
     */
    @ApiModelProperty("类型,1=评论,2=回复")
    Integer type = 1;

    @ApiModelProperty("我是否喜欢此评论,0=未喜欢, 2=喜欢，7=举报")
    Integer favorType = 0;

    @ApiModelProperty("被回复用户信息")
    UserShowVO toUser;

    /**
     * 评论内容
     */
    @ApiModelProperty("评论内容")
    String content = "";

    /**
     * 评论图片
     */
    @ApiModelProperty("评论图片")
    String commentUrl = "";

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Timestamp createTime;
}
