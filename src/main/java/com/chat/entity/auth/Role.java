package com.chat.entity.auth;

import com.chat.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;

/**
*/
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table
public class Role extends BaseEntity {
    private String name="";
}
