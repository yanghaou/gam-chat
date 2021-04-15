package com.chat.entity.favor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 谁查看了谁
 */
@Entity
@Data
@Table
@EntityListeners(AuditingEntityListener.class)
public class ViewMe implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 查看用户ID
     */
    Long fromUserId = 0L;

    /**
     * 被查看用户ID
     */
    Long toUserId = 0L;

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
