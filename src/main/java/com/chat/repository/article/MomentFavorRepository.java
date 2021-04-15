package com.chat.repository.article;

import com.chat.entity.article.MomentFavor;
import com.chat.entity.favor.ViewMe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MomentFavorRepository extends JpaRepository<MomentFavor, Long>, JpaSpecificationExecutor<MomentFavor> {
    
    int countByMomentIdAndTypeAndIsDel(Long id, int momentLoved, int notDel);

    MomentFavor findByMomentIdAndTypeAndUidAndIsDel(Long id, int momentLoved, Long uid, int notDel);

    List<MomentFavor> findByMomentIdAndTypeAndIsDel(Long id, int momentLoved, int notDel);

    @Query(value = "select moment_id from moment_favor where uid =?1 and type = ?2 and is_del= ?3",nativeQuery = true)
    List<Long> findMomentIdByUidAndTypeAndIsDel(Long uid, int momentNotLoved, int notDel);

    boolean existsByMomentIdAndTypeAndUidAndIsDel(Long momentId, Integer type, Long uid, int notDel);
}