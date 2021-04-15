package com.chat.entity.article;

import com.chat.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

/**
 * 文章关注
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table
@EntityListeners(AuditingEntityListener.class)
public class CommentFavor extends BaseEntity {
     /**
     * 文章id
     */
    Long commentId = 0L;

    Long uid = 0L;

    /**
     * 对文章的操作类型,2=点赞喜欢,7=举报
     */
    Integer type = 0;
}
