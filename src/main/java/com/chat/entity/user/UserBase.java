package com.chat.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * 用户基本信息。会改，但不频繁的。
*/
@Data
@Entity
@Table
@EntityListeners(AuditingEntityListener.class)
public class UserBase {
    //用户唯一id
    @Id
    @ApiModelProperty("用户id，前端值展示，更新时不用传")
    private Long uid;
    @ApiModelProperty("用户名，前端值展示，更新时不用传")
    private String username;
    @ApiModelProperty("昵称")
    String nickname="";

    /**
     * 年龄
     */
    Integer age = 0;
    @ApiModelProperty("yyyyMMdd")
    Integer birthday = 0;

    @ApiModelProperty("头像")
    String avatar="";
    @ApiModelProperty("临时头像")
    String avatarTemp="";
    @ApiModelProperty("头像状态。1=审核中,2=已通过,3=不通过")
    Integer avatarStatus = 1;

    /**
     * 性别。1=男，2=女，3=其他
     */
    @ApiModelProperty("性别.0=其他，1=男，2=女")
    Integer gender = 0;

    /**
     * 创建时间
     */
    @CreatedDate
    @JsonIgnore
    private Timestamp createTime ;

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
