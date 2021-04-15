package com.chat.vo.file;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class FieldVO {

    @ApiModelProperty("字段类型")
    @NotNull(message = "field name can't be empty")
    String fieldName;

    @ApiModelProperty("字段值")
    @NotNull(message = "field value can't be empty")
    String fieldValue;
}
