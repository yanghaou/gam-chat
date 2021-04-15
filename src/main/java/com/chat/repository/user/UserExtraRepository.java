package com.chat.repository.user;

import com.chat.entity.user.UserBase;
import com.chat.entity.user.UserExtra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserExtraRepository extends JpaRepository<UserExtra, Long>, JpaSpecificationExecutor<UserExtra> {
    UserExtra findByUidAndIsDel(Long uid, int notDel);
}