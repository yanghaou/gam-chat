package com.chat.entity.article;

import com.chat.entity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * 用户评论
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table
@EntityListeners(AuditingEntityListener.class)
public class MomentComment extends BaseEntity {
    //发起人用户ID
    Long uid = 0L;

    //被回复用户的ID
    Long toUid = 0L;

    @ApiModelProperty("1为审核中,2审核通过,3不通过")
    Integer verifyStatus = 2;
    @ApiModelProperty("审批意见")
    String verifyReason="";

    /**
     * 回复的评论id
     */
    Long commentId = 0L;

    //文章ID
    Long momentId = 0L;

    /**
     * 回复内容
     */
    String content = "";

    /**
     * 回复图片
     */
    String commentUrl = "";
}
