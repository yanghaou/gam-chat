package com.chat.vo.verify;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;


@ApiModel
@Data
@Accessors(chain = true)
public class MomentVerifyVO {

    @ApiModelProperty(value = "审批类型，6=文章，7=评论")
    Integer type;

    @ApiModelProperty(value = "列表")
    List<VerifyBatchVO> momentList;

}
