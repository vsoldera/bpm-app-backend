package com.app.springrolejwt.model.vo.userVos;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class DependentSingleVo {

    @NotNull
    @NotBlank
    String uuid;
}
