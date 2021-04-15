package com.chat.repository.user;


import com.chat.entity.user.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long>, JpaSpecificationExecutor<UserAccount> {

    boolean existsByAppTypeAndClientTypeAndUsernameAndIsDel(String appType, Integer clientType, String username, int isDel);

    boolean existsByAppTypeAndClientTypeAndEmailAndIsDel(String appType, Integer clientType, String email, int isDel);

    UserAccount findByAppTypeAndClientTypeAndEmailAndIsDel(String appType, Integer clientType, String email, int notDel);

    UserAccount findByAppTypeAndClientTypeAndUidAndIsDel(String appType, Integer clientType, Long uid, int notDel);

    UserAccount findByUidAndIsDel(Long uid, Integer isDel);

    boolean existsByUidAndIsDel(Long uid, Integer isDel);

    @Modifying
    @Transactional
    @Query(value="update user_account set account_status = ?3 , reason = ?4 where uid =?1 and is_del =?2", nativeQuery = true)
    void updateStatus(Long uid, Integer isDel, Integer accountStatus, String s);

    @Query(value = "select uid from user_account where app_type =?1 and is_del= ?2",nativeQuery = true)
    List<Long> findByAppTypeAndIsDel(String appType, int notDel);
}