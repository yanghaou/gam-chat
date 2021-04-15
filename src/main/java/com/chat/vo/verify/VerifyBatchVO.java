package com.chat.vo.verify;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@ApiModel
@Data
@Accessors(chain = true)
public class VerifyBatchVO {
    @ApiModelProperty("图片ID或用户ID")
    Long id;
    @ApiModelProperty("审核状态,1=通过，2=不通过")
    Integer status;
}
