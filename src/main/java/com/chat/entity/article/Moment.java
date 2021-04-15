package com.chat.entity.article;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 用户之间互动表
 */
@Entity
@Data
@Table
@EntityListeners(AuditingEntityListener.class)
public class Moment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 作者用户ID
     */
    Long authorId = 0L;

     /**
     * 文章内容
     */
    String content = "";

    /**
     * 图片地址
     */
    String picUrl = "";
    /**
     * 图片地址
     */
    String videoUrl = "";
    /**
     * 语音地址
     */
    String voiceUrl = "";

    @ApiModelProperty("1为审核中,2审核通过,3不通过")
    Integer verifyStatus = 1;
    @ApiModelProperty("审批意见")
    String verifyReason="";

    /**
     * 创建时间
     */
    @CreatedDate
    private Timestamp createTime;

    /**
     * 更新时间
     */
    @LastModifiedDate
    private Timestamp updateTime ;

    /**
     * 是否删除。0=未删除,1=已删除
     */
    private Integer isDel = 0;
}
