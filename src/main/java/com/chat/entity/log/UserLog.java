package com.chat.entity.log;

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
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class UserLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //用户唯一id
    private Long uid;
    //版本
    private String opVersion;
    //操作类型.1=注册，2=登录，3=修改，4=发布，5=关注
    private Integer opType;
    //操作类容
    private String opContent;
    //操作结果
    private Integer opResult;
    //操作id
    private String lastIp;
    //设备id
    private String deviceId;
    //操作系统
    private String osSystem;
    //操作系统版本
    private String osVer;

    /**
     * 创建时间
     */
    @CreatedDate
    private Timestamp createTime ;

    /**
     * 更新时间
     */
    @LastModifiedDate
    private Timestamp updateTime ;

    /**
     * 是否删除。0=未删除,1=已删除
     */
    private Integer isDel = 0;
}
