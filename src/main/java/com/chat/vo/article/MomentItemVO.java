package com.chat.vo.article;

import com.chat.vo.user.UserShowVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * 用户之间互动表
 */
@Data
public class MomentItemVO {
    @ApiModelProperty("文章id")
    Long id;

    /**
     * 作者
     */
    @ApiModelProperty("作者信息")
    UserShowVO author;

    /**
     * 文章内容
     */
    @ApiModelProperty("内容")
    String content = "";

    /**
     * 被查看次数
     */
    @ApiModelProperty("被查看次数")
    Integer viewedCnt = 0;

    /**
     * 被点赞次数
     */
    @ApiModelProperty("被点赞喜欢次数")
    Integer praiseCnt = 0;

    @ApiModelProperty("被评论次数")
    Integer commentCnt = 0;

    @ApiModelProperty("被分享次数")
    Integer shareCnt = 0;

    /**
     * 我是否点赞了该文章
     */
    @ApiModelProperty("我是否点赞了该文章,0=未点赞，1=已点赞")
    Integer praised = 0;

    @ApiModelProperty("我是否收藏了该文章,0=未收藏，1=已收藏")
    Integer keeped = 0;

    @ApiModelProperty("我是否分享了该文章,0=未分享，1=已分享")
    Integer shared = 0;

    /**
     * 作者
     */
    @ApiModelProperty("点赞用户列表")
    List<UserShowVO> praiseUserList;

    /**
     * 图片地址
     */
    @ApiModelProperty("图片")
    String picUrl = "";
    /**
     * 图片地址
     */
    @ApiModelProperty("视频")
    String videoUrl = "";
    /**
     * 语音地址
     */
    @ApiModelProperty("语音")
    String voiceUrl = "";
    /**
     * 创建时间
     */
    @ApiModelProperty("发布时间")
    Timestamp createTime;
}
