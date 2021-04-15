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
 * 用户基本信息。会改，但不频繁的。
*/
@Data
@Entity
@Table
@EntityListeners(AuditingEntityListener.class)
public class UserExtra {
    //用户唯一id
    @Id
    @ApiModelProperty("用户id，更新时不用传")
    Long uid;
    //使用本APP时间
    @ApiModelProperty("使用本APP时间")
    Double useTime=0d;
    //我的标签
    @ApiModelProperty("我的标签")
    String label="";
    //星座
    @ApiModelProperty("星座")
    String constellation="";
    //性取向
    @ApiModelProperty("性取向")
    String sexual="";
    //交友目的
    @ApiModelProperty("交友目的")
    String friendsIntention="";
    //职业
    @ApiModelProperty("职业")
    String profession="";
    //个性签名
    @ApiModelProperty("个性签名")
    String signature="";
    //喝酒程度
    String drink = "";
    //吸烟
    String smoke = "";
    //是否有房
    Integer haveHouse = 0;
    //头发长短
    String hairLength = "";
    // 头发形状
    String hairShape = "";
    //头发颜色
    String hairColor = "";
    //眼睛颜色
    String eyeColor = "";
    //身高
    String height = "";
    //身材
    String bodyType = "";
    //语言
    String language = "";
    //婚姻状态。0=未婚,1=已婚
    @ApiModelProperty("婚姻状态。0=未婚,1=已婚")
    Integer maritalStatus = 0;

    //aboutMe
    String aboutMe="";
    String aboutMeTemp="";
    //1为审核中，2为通过，3为驳回
    @ApiModelProperty("1为审核中，2为通过，3为不通过")
    Integer aboutMeStatus=1;
    @ApiModelProperty("aboutMe审批意见")
    String aboutMeReason="";

    //aboutMatch
    String aboutMatch="";
    String aboutMatchTemp="";
    //1为审核中，2为通过，3为驳回
    @ApiModelProperty("1为审核中，2为通过，3为不通过")
    Integer aboutMatchStatus=1;
    @ApiModelProperty("aboutMatch审批意见")
    String aboutMatchReason="";

    //个性
    String personality="";
    //教育程度
    String education="";
    String musicStyle="";
    String income="";
    String findGender="";
    String ageRange="";
    String findRelation="";
    String playRole="";
    String playRoleTime="";
    Integer haveCar=0;


    //扩展字段
    String spareStr1st="";
    String spareStr2st="";
    String spareStr3st="";
    String spareStr4st="";
    String spareStr5st="";
    String spareStr6st="";

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
