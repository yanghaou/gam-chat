package com.chat.repository.favor;

import com.chat.entity.favor.ViewMe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ViewMeRepository extends JpaRepository<ViewMe, Long>, JpaSpecificationExecutor<ViewMe> {

}