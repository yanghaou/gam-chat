package com.chat.repository.article;

import com.chat.entity.article.Moment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MomentRepository extends JpaRepository<Moment, Long>, JpaSpecificationExecutor<Moment> {
    Moment findByIdAndIsDel(Long id, int isDel);

    boolean existsByIdAndAuthorIdAndIsDel(Long id,Long uid, int isDel);

    @Modifying
    @Transactional
    @Query(value="update moment set is_del=1 where id =?1 and author_id =?2", nativeQuery = true)
    void deleteMoment(Long id, Long uid);

    @Modifying
    @Transactional
    @Query(value="update moment set verify_status = ?2 where id in ?1 ", nativeQuery = true)
    void updateStatus(List<Long> momentIdsPass, int verifyPassStatus);
}