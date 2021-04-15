package com.chat.service;

import com.chat.entity.map.DataRegion;
import com.chat.repository.map.DataRegionRepository;
import com.chat.vo.map.MapVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DataRegionService {
    @Autowired
    DataRegionRepository dataRegionRepository;

    //按父ID查询
    private List<MapVO> getRegionByPid(long l) {
        List<DataRegion> country = dataRegionRepository.findByPid(l);
        return country.parallelStream().map(dataRegion -> {
            MapVO vo = new MapVO();
            BeanUtils.copyProperties(dataRegion, vo);
            if (dataRegion.getLevel() != 4) {
                vo.setChild(getRegionByPid(dataRegion.getId()));
            }
            return vo;
        }).sorted(Comparator.comparing(MapVO::getCode,Comparator.nullsLast(String::compareTo))).collect(Collectors.toList());
    }

//    @Cacheable(cacheNames = "dataRegion",keyGenerator = "keyGenerator")
    public List<MapVO> getAllCountry() {
        //查询国家
        List<DataRegion> country = dataRegionRepository.findByLevel(2);
        return country.stream().map(dataRegion -> {
            MapVO vo = new MapVO();
            BeanUtils.copyProperties(dataRegion, vo);
            return vo;
        }).sorted(Comparator.comparing(MapVO::getCode,Comparator.nullsLast(String::compareTo)))
                .collect(Collectors.toList());
    }



    public List<MapVO> getProvinceAndCity(Long countryId) {
        //查询省份
        return getRegionByPid(countryId);
    }

    public List<MapVO> getAllCity(Long provinceId) {
        //查询省份
        return getRegionByPid(provinceId);
    }
}
