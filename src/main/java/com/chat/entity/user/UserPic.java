package com.chat.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 用户详情图片表,存储头像和相册
 */
@Entity
@Data
@Table
@EntityListeners(AuditingEntityListener.class)
public class UserPic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("图片id")
    private Long id;

    /**
     * 用户ID
     */
    Long uid;

    /**
     * 图片类型。1=头像,2=公有相册,3=私有相册
     */
    @ApiModelProperty("图片类型。1=头像,2=公有相册,3=私有相册")
    private Integer picType = 0;

    //图片排序
    @ApiModelProperty("图片排序")
    private Integer picOrder = 1;

    /**
     * 图片地址
     */
    @ApiModelProperty("图片地址")
    private String picUrl = "";

    /**
     * 图片的审核状态。
     */
    @ApiModelProperty("图片的审核状态。1=审核中,2=审核通过,3=审核不通过")
    private Integer verifyStatus = 1;

    @ApiModelProperty("审批意见")
    private String reason = "";

    /**
     * 创建时间
     */
    @CreatedDate
    @JsonIgnore
    private Timestamp createTime;

    /**
     * 更新时间
     */
    @LastModifiedDate
    @JsonIgnore
    private Timestamp updateTime ;

    /**
     * 是否删除。0=未删除,1=已删除
     */
    @JsonIgnore
    private Integer isDel = 0;
}
