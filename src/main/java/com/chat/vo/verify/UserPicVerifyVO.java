package com.chat.vo.verify;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.util.List;

@ApiModel
@Data
@Accessors(chain = true)
public class UserPicVerifyVO{
    @ApiModelProperty(value = "批量审核图片")
    List<VerifyBatchVO> pics;
}
