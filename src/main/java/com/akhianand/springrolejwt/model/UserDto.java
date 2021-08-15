package com.akhianand.springrolejwt.model;

import lombok.Data;

@Data
public class UserDto {
    
    private String username;
    private String password;
    private String email;
    private String phone;
    private String name;
    private String businessTitle;

    public Users getUserFromDto(){
        Users user = new Users();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setPhone(phone);
        user.setName(name);
        
        return user;
    }
    
}