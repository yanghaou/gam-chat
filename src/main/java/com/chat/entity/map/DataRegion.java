package com.chat.entity.map;

import com.chat.entity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table
@Data
public class DataRegion extends BaseEntity{
    @ApiModelProperty("父ID")
    Long pid;
    @ApiModelProperty("路径")
    String path;
    @ApiModelProperty("层级")
    Integer level;
    @ApiModelProperty("中文名称")
    String name;
    @ApiModelProperty("英文名称")
    String nameEn;
    @ApiModelProperty("中文拼音")
    String namePinyin;
    @ApiModelProperty("代码")
    String code;

}
