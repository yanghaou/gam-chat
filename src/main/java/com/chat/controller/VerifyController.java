package com.chat.controller;

import com.chat.common.CommonResult;
import com.chat.common.PageView;
import com.chat.service.MomentService;
import com.chat.service.UserService;
import com.chat.vo.verify.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "审核相关接口")
@RestController
public class VerifyController extends BaseController{
    @Autowired
    UserService userService;
    @Autowired
    MomentService momentService;

    @ApiOperation(value = "查询所有待审核头像")
    @GetMapping("/verify/user/avatar/list")
    public CommonResult<PageView<UserAvatarItemVO>> getAvatarList(@RequestParam(defaultValue = "1") Integer page,
                                                                  @RequestParam(defaultValue = "200") Integer pageSize,
                                                                  @RequestParam Integer gender,
                                                                  @RequestParam String appType
                                                                  ){
        PageView<UserAvatarItemVO> pageView = userService.getVerifyAvatarList(appType,gender,page,pageSize);
        return CommonResult.success(pageView);
    }

    @ApiOperation(value = "查询所有待审核的About信息")
    @GetMapping("/verify/user/about/list")
    public CommonResult<PageView<UserAboutItemVO>> getAboutList(@RequestParam(defaultValue = "1") Integer page,
                                                                @RequestParam(defaultValue = "200") Integer pageSize,
                                                                @ApiParam("1=aboutMe, 2=aboutMatch") @RequestParam Integer type
    ){
        PageView<UserAboutItemVO> pageView = userService.getVerifyAboutList(page,pageSize,type);
        return CommonResult.success(pageView);
    }

    @ApiOperation(value = "查询所有待审核的相册")
    @GetMapping("/verify/user/pic/list")
    public CommonResult<PageView<UserPicItemVO>> getPicList(@RequestParam(defaultValue = "1") Integer page,
                                                            @RequestParam(defaultValue = "200") Integer pageSize,
                                                            @RequestParam String appType){
        PageView<UserPicItemVO> pageView = userService.getVerifyPicList(appType,page,pageSize);
        return CommonResult.success(pageView);
    }


    @ApiOperation(value = "用户信息审核")
    @PostMapping("/verify/user/detail")
    public CommonResult verifyUser(@Valid @RequestBody UserDetailVerifyVO vo, BindingResult bindingResult){
        checkParam(bindingResult);
        userService.verifyUserDetail(vo);
        return CommonResult.success();
    }

    @ApiOperation(value = "相册审核")
    @PostMapping("/verify/user/pic")
    public CommonResult verifyUserPic(@Valid @RequestBody UserPicVerifyVO vo, BindingResult bindingResult){
        checkParam(bindingResult);
        userService.verifyUserPic(vo);
        return CommonResult.success();
    }

    @ApiOperation(value = "审批文章和评论")
    @PostMapping("/verify/moment/verify")
    public CommonResult momentVerify(@RequestBody MomentVerifyVO vo) {
        momentService.verifyMoment(vo);
        return CommonResult.success();
    }

    @ApiOperation(value = "查询所有待审核的动态和评论信息")
    @GetMapping("/verify/moment/list")
    public CommonResult<PageView<MomentItemVerifyVO>> getMomentList(@RequestParam(defaultValue = "1") Integer page,
                                                                    @RequestParam(defaultValue = "200") Integer pageSize,
                                                                    @ApiParam("1=动态, 2=评论") @RequestParam Integer type
    ){
        PageView<MomentItemVerifyVO> pageView = momentService.getVerifyMomentList(page,pageSize,type);
        return CommonResult.success(pageView);
    }

}
