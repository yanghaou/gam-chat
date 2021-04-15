package com.chat.repository.user;

import com.chat.entity.user.UserBase;
import com.chat.entity.user.UserPic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserBaseRepository extends JpaRepository<UserBase, Long>, JpaSpecificationExecutor<UserBase> {
    UserBase findByUidAndIsDel(Long uid, int notDel);

    List<UserBase> findByUidInAndIsDel(List<Long> userIds, int notDel);

    @Modifying
    @Transactional
    @Query(value="update user_base set avatar = ?2 where uid =?1 ", nativeQuery = true)
    void updateAvatar(Long uid, String avatar);

    List<UserBase> findByAvatarStatusAndIsDel(int verifyPassIn, int notDel);

}