package com.chat.vo.article;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CommentFormVO {
    @ApiModelProperty("文章id")
    @NotNull(message = "momentId can't be empty")
    Long momentId;

    /**
     * 对文章的操作类型,1=查看,2=点赞喜欢,3=不喜欢,4=评论,5=收藏,6=转发,7=举报
     */
    @ApiModelProperty("1=查看,2=点赞,3=不喜欢,4=评论,5=收藏,6=转发,7=举报")
    @NotNull(message = "type can't be empty")
    Integer type;

    /**
     * 评论内容
     */
    @ApiModelProperty("评论内容或举报原因")
    String content = "";

    /**
     * 评论图片
     */
    @ApiModelProperty("评论图片")
    String commentUrl = "";
}
