package com.chat.vo.map;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel
public class MapVO implements Serializable {
    @ApiModelProperty("区域ID")
    Long id;
    @ApiModelProperty("中文名称")
    String name;
    @ApiModelProperty("英文名称")
    String nameEn;
    @ApiModelProperty("中文拼音")
    String namePinyin;
    @ApiModelProperty("代码")
    String code;
    @ApiModelProperty("层级。2=国家，3=省份，4=城市")
    Integer level;

    @ApiModelProperty("子节点")
    List<MapVO> child;
}
