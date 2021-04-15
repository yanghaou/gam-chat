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
public class UserPicItemVO {
    @ApiModelProperty("图片ID")
    Long id;
    @ApiModelProperty("待审的图片地址")
    String picUrl;
}
