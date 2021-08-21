package com.app.springrolejwt.model;

import com.gitee.hengboy.mybatis.enhance.common.enums.KeyGeneratorTypeEnum;
import lombok.Data;

import javax.persistence.*;
import java.security.Timestamp;

@Entity
@Table(name = "phone_code")
@Data
public class PhoneCode {

    @Column(name = "pc_id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "pc_phone")
    private String phone;

    @Column(name = "pc_code")
    private String code;

    @Column(name = "pc_create_time")
    private Timestamp createTime;
}

