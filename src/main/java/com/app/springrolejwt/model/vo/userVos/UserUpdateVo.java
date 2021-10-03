package com.app.springrolejwt.model.vo.userVos;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@Data
public class UserUpdateVo {

    @NotBlank
    @NotNull
    private String completeName;

    private Set<String> role;

    @NotBlank
    @NotNull
    private String birthDate;

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
