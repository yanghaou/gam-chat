package com.chat.controller;

import com.chat.common.CommonResult;
import com.chat.service.FileUploadService;
import com.chat.vo.file.FileToken;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Api(tags = "文件相关接口")
@RestController
public class FileController {
    @Autowired
    FileUploadService fileUploadService;
    @Value("${qiniu.domain}")
    String qiNiuDomain;

    @ApiOperation(value = "获取七牛云文件上传token")
    @GetMapping("/file/upload/token")
    public CommonResult<FileToken> getPicToken(){
        FileToken fileToken = fileUploadService.getQiNiuUploadToken();
        return CommonResult.success(fileToken);
    }

    @ApiOperation(value = "获取七牛云域名")
    @GetMapping("/file/domain")
    public CommonResult getQiNiuDomain(){
        Map<String,String> map = Maps.newHashMap();
        map.put("domain",qiNiuDomain);
        return CommonResult.success(map);
    }


}
