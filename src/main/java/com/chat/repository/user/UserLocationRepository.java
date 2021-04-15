package com.chat.repository.user;

import com.chat.entity.user.UserBase;
import com.chat.entity.user.UserLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserLocationRepository extends JpaRepository<UserLocation, Long>, JpaSpecificationExecutor<UserLocation> {
    UserLocation findByUidAndIsDel(Long uid, int notDel);
}