package com.chat.repository.article;

import com.chat.entity.article.MomentComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MomentCommentRepository extends JpaRepository<MomentComment, Long>, JpaSpecificationExecutor<MomentComment> {

    boolean existsByIdAndUidAndIsDel(Long id, Long userId, int isDel);

    @Transactional
    @Modifying
    @Query(value="update moment_comment set is_del=1 where id =?1 and user_id =?2", nativeQuery = true)
    void delByIdAndUserId(Long picId, Long uid);

    int countByMomentIdAndIsDel(Long id, int notDel);
    @Modifying
    @Transactional
    @Query(value="update moment_comment set verify_status = ?2 where id in ?1 ", nativeQuery = true)
    void updateStatus(List<Long> commentsNotPass, int verifyNotPassStatus);
}