package com.chat.vo.verify;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ApiModel
@Data
@Accessors(chain = true)
public class UserDetailVerifyBaseVO implements Serializable {

    @ApiModelProperty(value = "被审核的用户uid，必传")
    @NotNull(message = "verify uid can't be empty")
    Long verifyUid;

    @ApiModelProperty(value = "审核结果。1=通过,2=不通过")
    @NotNull(message = "verify status can't be empty")
    Integer status;

    @ApiModelProperty(value = "原因,选填")
    String reason = "";

}
