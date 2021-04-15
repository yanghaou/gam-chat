package com.chat.vo.verify;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;


@ApiModel
@Data
@Accessors(chain = true)
public class VerifyBaseVO implements Serializable {

    @ApiModelProperty(value = "审核结果。1=通过,2=不通过")
    Integer status;

    @ApiModelProperty(value = "原因,选填")
    String reason = "";

}
