package com.chat.repository.favor;

import com.chat.entity.favor.Interaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface InteractionRepository extends JpaRepository<Interaction, Long>, JpaSpecificationExecutor<Interaction> {
    List<Interaction> getByFromUserIdAndTypeAndIsDel(Long from,Integer type, Integer isDel);
    Integer countByFromUserIdAndTypeAndIsDel(Long from,Integer type, Integer isDel);

    List<Interaction> getByToUserIdAndTypeAndIsDel(Long to,Integer type, Integer isDel);
    Integer countByToUserIdAndTypeAndIsDel(Long to,Integer type, Integer isDel);

    Interaction findInteractionByFromUserIdAndToUserIdAndTypeAndIsDel(Long from, Long to,Integer type, Integer isDel);

    boolean existsByFromUserIdAndToUserIdAndTypeAndIsDel(Long from, Long to,Integer type, Integer isDel);


}