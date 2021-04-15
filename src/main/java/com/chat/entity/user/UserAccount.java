package com.chat.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * 用户账号。不会改的数据
*/
@Data
@Entity
@Table
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class UserAccount {
    //用户唯一id
    @Id
    private Long uid;

    private String appType;

    private Integer clientType;

    /**
     * 用户名
     */
    private String username = "";

    /**
     * 用户名,邮箱
     */
    private String email = "";

    /**
     * 密码
     */
    @JsonIgnore
    private String password = "";

    @ApiModelProperty("账号状态（int,默认为0为新创建，1为审核中，2为审核通过，3为审核不通过，4为用户账号删除，5为账号暂停使用）")
    private Integer accountStatus = 0;
    @ApiModelProperty("审批意见")
    private String reason = "";

    /**
     * 创建时间
     */
    @CreatedDate
//    @JsonIgnore
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
