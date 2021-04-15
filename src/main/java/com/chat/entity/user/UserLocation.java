package com.chat.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * 用户位置
*/
@Data
@Entity
@Table
@EntityListeners(AuditingEntityListener.class)
public class UserLocation {
    //用户唯一id
    @Id
    @ApiModelProperty("用户id，更新时不用传")
    private Long uid;

    //国家名称
    @ApiModelProperty("国家")
    String country="";
    //国家id
    Integer countryId=0;
    //省名称
    @ApiModelProperty("省份")
    String province="";
    //省id
    Integer provinceId=0;
    //城市名称
    @ApiModelProperty("城市")
    String city="";
    //城市id
    Integer cityId=0;
    //具体地址
    @ApiModelProperty("详情地址")
    String address="";

    /**
     * 创建时间
     */
    @CreatedDate
    @JsonIgnore
    private Timestamp createTime ;

    /**
     * 更新时间
     */
    @LastModifiedDate
    @JsonIgnore
    private Timestamp updateTime ;

    /**
     * 是否删除。0=未删除,1=已删除
     */
    @JsonIgnore
    private Integer isDel = 0;

}
