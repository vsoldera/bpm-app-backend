package com.example.bpmappbackend.service;

import com.example.bpmappbackend.domain.AppUser;
import com.example.bpmappbackend.domain.Role;

import java.util.List;

public interface UserService {
    AppUser saveUser(AppUser user);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    AppUser getUser(String username);
    List<AppUser> getUsers();
}
