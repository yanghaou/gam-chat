package com.chat.entity.favor;

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
public class Interaction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 主动用户ID
     */
    Long fromUserId = 0L;

    /**
     * 被动用户ID
     */
    Long toUserId = 0L;

     /**
     * 操作类型。1=winked at me, 2=favor
     */
    Integer type = 0;

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
