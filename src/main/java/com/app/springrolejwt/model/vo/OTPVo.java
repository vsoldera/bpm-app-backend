package com.app.springrolejwt.model.vo;

import lombok.Data;

@Data
public class OTPVo {

    private String phone;
    private String otp;
    private String expireTime;


}
