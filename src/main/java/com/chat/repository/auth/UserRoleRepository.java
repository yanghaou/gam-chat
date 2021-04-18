package com.chat.repository.auth;

import com.chat.entity.auth.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, Long>, JpaSpecificationExecutor<UserRole> {

    List<UserRole> findByUidAndIsDel(Long uid, int notDel);

    @Query(value = "select uid from user_role where role_id = ?1 and is_del = ?2",nativeQuery = true)
    List<Long> findByRoleIdAndIsDel(Long roleId, int notDel);
}