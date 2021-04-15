package com.chat.repository.article;

import com.chat.entity.article.CommentFavor;
import com.chat.entity.article.MomentFavor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface CommentFavorRepository extends JpaRepository<CommentFavor, Long>, JpaSpecificationExecutor<CommentFavor> {
    
    int countByCommentIdAndTypeAndIsDel(Long id, int momentLoved, int notDel);

    boolean existsByCommentIdAndTypeAndUidAndIsDel(Long id, int momentLoved, Long uid, int notDel);

    List<CommentFavor> findByCommentIdAndTypeAndIsDel(Long id, int momentLoved, int notDel);
}