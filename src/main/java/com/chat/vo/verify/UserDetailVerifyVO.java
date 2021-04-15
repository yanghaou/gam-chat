package com.chat.vo.verify;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;


@ApiModel
@Data
@Accessors(chain = true)
public class UserDetailVerifyVO implements Serializable {
    @ApiModelProperty(value = "审核类型。1=avatar,2=AboutMe,3=AboutMatch,4=账号审核(修改accountStatus)")
    @NotNull(message = "verify type can't be empty")
    Integer type;

    @ApiModelProperty(value = "审核详情")
    @NotNull(message = "verify detail can't be empty")
    @Valid
    List<UserDetailVerifyBaseVO> verifyDetail;

}
