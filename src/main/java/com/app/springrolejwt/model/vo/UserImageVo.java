package com.app.springrolejwt.model.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UserImageVo {

    @NotNull
    @NotBlank
    String file;

    @NotNull
    @NotBlank
    String imageType;

    @NotNull
    @NotBlank
    String docType;
}
