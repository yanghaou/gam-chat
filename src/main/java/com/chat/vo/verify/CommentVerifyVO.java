package com.chat.vo.verify;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


@EqualsAndHashCode(callSuper = true)
@ApiModel
@Data
@Accessors(chain = true)
public class CommentVerifyVO extends VerifyBaseVO{
    @ApiModelProperty(value = "评论ID")
    Long commentId;
}
