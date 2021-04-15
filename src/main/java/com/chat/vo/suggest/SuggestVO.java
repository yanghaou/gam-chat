package com.chat.vo.suggest;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import java.sql.Timestamp;

@Data
@ApiModel
@Accessors(chain = true)
public class SuggestVO {
    @ApiModelProperty("id,只展示，保存时不传")
    Long id;
    @ApiModelProperty("uid，保存时不传")
    Long uid;
    @ApiModelProperty("建议标题")
    @NotEmpty(message = "建议标题不能为空")
    String title;
    @ApiModelProperty("建议内容")
    @NotEmpty(message = "建议内容不能为空")
    String content;
    @ApiModelProperty("建议图片")
    String url;
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm")
    Timestamp createTime;
}
