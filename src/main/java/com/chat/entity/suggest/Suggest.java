package com.chat.entity.suggest;


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
public class Suggest extends BaseEntity {
    @ApiModelProperty("用户uid")
    Long uid;
    @ApiModelProperty("建议标题")
    String title;
    @ApiModelProperty("建议内容")
    String content;
    @ApiModelProperty("建议图片")
    String url;
}
