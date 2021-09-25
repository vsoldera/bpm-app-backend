package com.app.springrolejwt.model.vo.userVos;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ResponsibleVo {

    @NotNull
    @NotBlank
    private String completeName;

    private String uuid;

    private String phone;

    private String photoPath;

}
