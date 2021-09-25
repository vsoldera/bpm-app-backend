package com.app.springrolejwt.model.vo.userVos;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class UserHealthVo {

    @NotNull
    @NotBlank
    private String status;

    @NotNull
    private Integer heartBeat;

    @NotNull
    private Float latitude;

    @NotNull
    private Float longitude;

    @NotNull
    private Integer cardiacSteps;

}
