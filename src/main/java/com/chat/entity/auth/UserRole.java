package com.chat.entity.auth;

import com.chat.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
*/
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table
public class UserRole extends BaseEntity {
    private Long uid;
    private Long roleId;
}
