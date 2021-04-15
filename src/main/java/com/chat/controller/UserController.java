package com.chat.controller;

import com.chat.common.CommonResult;
import com.chat.common.PageView;
import com.chat.service.UserService;
import com.chat.vo.common.IdAndNameVO;
import com.chat.vo.file.PicVO;
import com.chat.vo.user.*;
import com.chat.vo.verify.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "用户相关接口")
@RestController
public class UserController extends BaseController {

    @Autowired
    UserService userService;

    @ApiOperation(value = "用户注册接口")
    @PostMapping("/user/register")
    public CommonResult register(@RequestBody UserRegisterVO vo){
        userService.register(vo);
        return CommonResult.success();
    }

    @ApiOperation(value = "用户登录接口")
    @PostMapping("/user/login")
    public CommonResult<UserLoginResVO> login(@Valid @RequestBody UserLoginVO vo, BindingResult result){
        checkParam(result);
        return CommonResult.success(userService.login(vo));
    }

    @ApiOperation(value = "用户修改密码接口")
    @PostMapping("/user/update/password")
    public CommonResult updatePassword(@Valid @RequestBody UserPassUpdateVO vo, BindingResult result){
        checkParam(result);
        userService.updatePassword(vo);
        return CommonResult.success();
    }

    @ApiOperation(value = "给用户邮箱发送验证码")
    @GetMapping("/user/send/verifyCode")
    public CommonResult sendCode(@RequestParam String email){
        userService.sendCode(email);
        return CommonResult.success("The verify code has been sent to your email and will be valid within ten minutes, please check your mail box.");
    }

    @ApiOperation(value = "用户忘记密码，输入验证码，重新设置新密码")
    @PostMapping("/user/forget/password")
    public CommonResult forgetPassword(@Valid @RequestBody UserForgetPassVO vo, BindingResult result){
        checkParam(result);
        userService.forgetPassword(vo);
        return CommonResult.success();
    }


    @ApiOperation(value = "用户详情保存和更新")
    @PostMapping("/user/detail")
    public CommonResult save(@Valid @RequestBody UserDetailVO vo, BindingResult result){
        checkParam(result);
        userService.save(vo);
        return CommonResult.success();
    }

    @ApiOperation(value = "查询自己的详情信息(只能查自己的，且内容是全部信息)")
    @GetMapping("/user/detail")
    public CommonResult<UserDetailVO> getDetail(){
        UserDetailVO userVO = userService.getDetail();
        return CommonResult.success(userVO);
    }

    @ApiOperation(value = "查询用户基本信息-可以查询所有人的")
    @GetMapping("/user/show/detail")
    public CommonResult<UserShowVO> getShowDetail(@ApiParam("被查询用户uid") @RequestParam Long uid){
        UserShowVO userVO = userService.getShowDetail(uid);
        return CommonResult.success(userVO);
    }

    @ApiOperation(value = "用户保存图片")
    @PostMapping("/user/save/picture")
    public CommonResult<IdAndNameVO> savePicture(@RequestBody PicVO vo, BindingResult result){
        checkParam(result);
        return CommonResult.success(userService.savePicture(vo));
    }

    @ApiOperation(value = "用户删除图片")
    @GetMapping("/user/delete/picture")
    public CommonResult delPicture(@ApiParam("图片ID") @RequestParam Long picId){
        userService.deletePic(picId);
        return CommonResult.success();
    }


    @ApiOperation(value = "获取用户腾讯IM签名")
    @GetMapping("/user/im/signature")
    public CommonResult<String> getUserSig(){
        String sig = userService.getUserSig();
        return CommonResult.success(sig);
    }

    @ApiOperation(value = "关注或取消用户保存接口")
    @GetMapping("/user/favor")
    public CommonResult saveFavor(@ApiParam("被关注或取消用户ID") @RequestParam Long userId,
                                  @ApiParam("关注或取消.1=关注，2=取消") @RequestParam Integer type
                                  ){
        userService.saveFavor(userId,type);
        return CommonResult.success();
    }

    @ApiOperation(value = "关注用户查看接口")
    @GetMapping("/user/favor/list")
    public CommonResult<PageView<UserShowVO>> getFavor(@RequestParam Integer page,
                                                       @RequestParam Integer pageSize,
                                                       @ApiParam("favorType 关注类型，1=被别人关注，2=我关注别人")@RequestParam Integer favorType){
        PageView<UserShowVO> pageView = userService.getByFavorType(page,pageSize,favorType);
        return CommonResult.success(pageView);
    }

    @ApiOperation(value = "用户查看接口")
    @PostMapping("/user/list")
    public CommonResult<PageView<UserShowVO>> getUser(@RequestBody UserSearchVO vo){
        PageView<UserShowVO> pageView = userService.searchUserList(vo);
        return CommonResult.success(pageView);
    }



}
