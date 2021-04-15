package com.chat.repository.map;

import com.chat.entity.map.DataRegion;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@CacheConfig(cacheNames = "dataRegion")
public interface DataRegionRepository extends JpaRepository<DataRegion, Long>, JpaSpecificationExecutor<DataRegion> {

    @Cacheable(keyGenerator = "keyGenerator")
    List<DataRegion> findByLevel(int i);

    List<DataRegion> findByPid(long i);
}