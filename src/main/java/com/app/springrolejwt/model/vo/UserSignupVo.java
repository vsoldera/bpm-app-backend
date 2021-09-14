package com.app.springrolejwt.model.vo;

import com.app.springrolejwt.model.vo.validation.ValidPhoneNumber;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

@Data
public class UserSignupVo {
    @NotBlank
    private String username;

    @NotBlank
    @NotNull
    private String completeName;

    private Set<String> role;

    @NotBlank
    @NotNull
    private Date birthDate;

    @NotBlank
    @NotNull
    private Integer weight;

    @NotBlank
    @NotNull
    private Integer height;

    @NotBlank
    @NotNull
    private Boolean sex;

    @NotBlank
    @NotNull
    private Boolean isWheelchairUser;

    @NotBlank
    @NotNull
    private Boolean hasAlzheimer;

}
