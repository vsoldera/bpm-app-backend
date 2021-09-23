package com.app.springrolejwt.model.vo;

import com.app.springrolejwt.model.vo.validation.ValidPhoneNumber;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@Data
@JsonIgnoreProperties(ignoreUnknown = false)
public class UserSignupVo {

    @NotBlank(message = "Por favor, preencha o campo de nome")
    @NotNull(message = "Por favor, preencha o campo de nome")
    private String completeName;

    private Set<String> role;

    @NotNull(message = "Por favor, preencha o campo data")
    private Date birthDate;

    @NotNull(message = "Por favor, preencha o campo peso")
    private Integer weight;

    @NotNull(message = "Por favor, preencha o campo altura")
    private Integer height;

    @NotNull(message = "Por favor, preencha o campo sexo")
    private Boolean sex;

    @NotNull(message = "Por favor, preencha o campo de cadeirante")
    private Boolean isWheelchairUser;

    @NotNull(message = "Por favor, preencha o campo de alzheimer")
    private Boolean hasAlzheimer;

}
