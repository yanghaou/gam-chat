package com.chat.vo.file;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@ApiModel
public class PicVO {
    @ApiModelProperty("图片类型")
    @NotNull(message = "1=avatar，2=public pic, 3=private pic")
    Integer picType;
    @ApiModelProperty("图片地址")
    @NotEmpty(message = "picUrl can't be empty")
    String picUrl;
    @ApiModelProperty("图片排序,默认1")
    Integer picOrder = 1;
}
