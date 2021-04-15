package com.chat.controller;

import com.chat.common.CommonResult;
import com.chat.common.PageView;
import com.chat.service.SuggestService;
import com.chat.vo.suggest.SuggestVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "建议相关接口")
@RestController
public class SuggestController extends BaseController{
    @Autowired SuggestService suggestService;

    @GetMapping("/suggest/list")
    @ApiOperation(value = "分页查询建议,可以指定人查询")
    public CommonResult<PageView<SuggestVO>> getList(@RequestParam(defaultValue = "1",required = false) Integer page,
                                                     @RequestParam(defaultValue = "10",required = false) Integer pageSize,
                                                     @ApiParam("指定人查询,可以不传") @RequestParam(required = false) Long uid
                                                     ){
        PageView<SuggestVO> pageView = suggestService.getListByPage(page,pageSize,uid);
        return CommonResult.success(pageView);
    }

    @GetMapping("/suggest/detail")
    @ApiOperation(value = "查询建议详情")
    public CommonResult<SuggestVO> getList(@ApiParam("建议id") @RequestParam Long id){
        SuggestVO detail = suggestService.getDetailById(id);
        return CommonResult.success(detail);
    }

    @GetMapping("/suggest/delete")
    @ApiOperation(value = "删除建议")
    public CommonResult delById(@ApiParam("建议id") @RequestParam Long id){
        suggestService.delById(id);
        return CommonResult.success();
    }

    @PostMapping("/suggest/save")
    @ApiOperation(value = "保存建议")
    public CommonResult getList(@Valid @RequestBody SuggestVO vo, BindingResult bindingResult){
        checkParam(bindingResult);
        suggestService.save(vo);
        return CommonResult.success();
    }


}
