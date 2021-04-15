package com.chat.controller;

import com.chat.common.CommonResult;
import com.chat.service.DataRegionService;
import com.chat.vo.map.MapVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "地图相关接口")
@RestController
public class MapController {
    @Autowired
    DataRegionService dataRegionService;

    @GetMapping("/map/country/list")
    @ApiOperation(value = "查询所有国家")
    public CommonResult<List<MapVO>> getCountryList(){
        List<MapVO> mapVO = dataRegionService.getAllCountry();
        return CommonResult.success(mapVO);
    }

    @GetMapping("/map/provinceCity/list")
    @ApiOperation(value = "查询国家下所有省份和省份下的城市")
    public CommonResult<List<MapVO>> getProvinceList(@ApiParam("国家ID") @RequestParam Long countryId){
        List<MapVO> mapVO = dataRegionService.getProvinceAndCity(countryId);
        return CommonResult.success(mapVO);
    }

//    @GetMapping("/map/city/list")
//    @ApiOperation(value = "查询省份下所有城市列表")
//    public CommonResult<List<MapVO>> getCityList(@ApiParam("省份ID") @RequestParam Long provinceId){
//        List<MapVO> mapVO = dataRegionService.getAllCity(provinceId);
//        return CommonResult.success(mapVO);
//    }


}
