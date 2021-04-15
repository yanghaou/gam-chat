package com.chat.vo.article;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ReplyFormVO {
    @ApiModelProperty("被回复评论的id")
    @NotNull(message = "commentId can't be empty")
    Long commentId;

    @ApiModelProperty("文章id")
    @NotNull(message = "momentId can't be empty")
    Long momentId;

    @ApiModelProperty("被回复用户ID")
    @NotNull(message = "reply uid can't be empty")
    Long toUid;

    /**
     * 评论内容
     */
    @ApiModelProperty("回复内容")
    @NotNull(message = "reply content can't be empty")
    String content = "";

    /**
     * 评论图片
     */
    @ApiModelProperty("回复图片")
    String commentUrl = "";
}
