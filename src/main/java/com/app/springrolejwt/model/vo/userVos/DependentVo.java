package com.app.springrolejwt.model.vo.userVos;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
public class DependentVo {

    @NotNull
    @NotBlank
    private Set<String> responsible;

}
