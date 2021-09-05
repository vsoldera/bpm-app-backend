package com.app.springrolejwt.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
public class UserDataVo {
    private String username;
    private String completeName;
    private String email;
    private List<String> roles;
    private Date birthDate;
    private Integer weight;
    private Integer height;
    private Boolean sex;
    private Boolean isWheelchairUser;
    private Boolean hasAlzheimer;
    private String phone;

    public UserDataVo(String username, String completeName, String email, List<String> roles, Date birthDate, Integer weight, Integer height, Boolean sex, Boolean isWheelchairUser, Boolean hasAlzheimer, String phone) {
        this.username = username;
        this.completeName = completeName;
        this.email = email;
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
