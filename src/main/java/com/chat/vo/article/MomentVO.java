package com.chat.vo.article;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 用户发布文章
 */
@Data
public class MomentVO {
    /**
     * 文章内容
     */
    @ApiModelProperty("文章内容")
    @NotEmpty(message = "content can't be empty")
    String content;
    /**
     * 图片地址
     */
    @ApiModelProperty("图片")
    String picUrl = "";
    /**
     * 视频地址
     */
    @ApiModelProperty("视频")
    String videoUrl = "";
    /**
     * 语音地址
     */
    @ApiModelProperty("语音")
    String voiceUrl = "";

}
