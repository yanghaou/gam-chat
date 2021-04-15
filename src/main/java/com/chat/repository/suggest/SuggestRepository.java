package com.chat.repository.suggest;

import com.chat.entity.suggest.Suggest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface SuggestRepository extends JpaRepository<Suggest, Long>, JpaSpecificationExecutor<Suggest> {

    Suggest findByIdAndIsDel(Long id, int notDel);

    @Transactional
    @Modifying
    @Query(value="update suggest set is_del=1 where id =?1", nativeQuery = true)
    void delById(Long id);
}