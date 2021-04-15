package com.chat.vo.verify;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel
@NoArgsConstructor
@AllArgsConstructor
public class MomentItemVerifyVO {
    @ApiModelProperty("ID")
    Long id;
    @ApiModelProperty("内容")
    String content;
    @ApiModelProperty("图片地址")
    String picUrl;

}
