package com.chat.entity.article;

import com.chat.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * 文章关注
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table
@EntityListeners(AuditingEntityListener.class)
public class MomentFavor extends BaseEntity {
     /**
     * 文章id
     */
    Long momentId = 0L;

    Long uid = 0L;

    /**
     * 对文章的操作类型,1=查看,2=点赞,3=不喜欢,5=收藏,6=转发
     */
    Integer type = 0;
}
