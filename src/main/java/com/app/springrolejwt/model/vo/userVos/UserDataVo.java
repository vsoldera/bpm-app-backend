package com.app.springrolejwt.model.vo.userVos;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
public class UserDataVo {
    private String username;
    private String uuid;
    private String completeName;
    private List<String> roles;
    private String birthDate;
    private Integer weight;
    private Integer height;
    private String sex;
    private Boolean isWheelchairUser;
    private Boolean hasAlzheimer;
    private String phone;

    public UserDataVo(String username, String uuid, String completeName, List<String> roles, String birthDate, Integer weight, Integer height, String sex, Boolean isWheelchairUser, Boolean hasAlzheimer, String phone) {
        this.username = username;
        this.uuid = uuid;
        this.completeName = completeName;
        this.roles = roles;
        this.birthDate = birthDate;
        this.weight = weight;
        this.height = height;
        this.sex = sex;
        this.isWheelchairUser = isWheelchairUser;
        this.hasAlzheimer = hasAlzheimer;
        this.phone = phone;
    }
}
