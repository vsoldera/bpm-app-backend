package com.app.springrolejwt.model.vo;

import com.app.springrolejwt.model.vo.validation.ValidPhoneNumber;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class UserSignupVo {
    @NotBlank
    private String username;
 
    @NotBlank
    @Email
    private String email;
    
    private Set<String> role;

    @ValidPhoneNumber
    private String phone;
    
    @NotBlank
    private String password;

}
