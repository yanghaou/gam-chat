package com.chat.repository.user;

import com.chat.entity.user.UserPic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserPicRepository extends JpaRepository<UserPic, Long>, JpaSpecificationExecutor<UserPic> {

    @Modifying
    @Transactional
    @Query(value="update user_pic set is_del=1 where id =?1 and uid =?2", nativeQuery = true)
    void delPicByIdAndUserId(Long picId, Long uid);

    List<UserPic> findByUidAndIsDel(Long uid, int notDel);


    List<UserPic> findByIdInAndIsDel(List<Long> picId, int notDel);

    @Modifying
    @Transactional
    @Query(value="update user_pic set verify_status = ?2 where id in ?1", nativeQuery = true)
    void updateStatusByIdsIn(List<Long> passIds, int verifyPassStatus);
}