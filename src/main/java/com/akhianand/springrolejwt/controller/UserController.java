package com.akhianand.springrolejwt.controller;

import com.akhianand.springrolejwt.config.TokenProvider;
import com.akhianand.springrolejwt.model.AuthToken;
import com.akhianand.springrolejwt.model.LoginUser;
import com.akhianand.springrolejwt.model.Users;
import com.akhianand.springrolejwt.model.UserDto;
import com.akhianand.springrolejwt.service.UserService;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/users")
@Log4j2
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider jwtTokenUtil;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    @CrossOrigin(origins = "http://localhost:8080")
    public ResponseEntity<?> generateToken(@RequestBody LoginUser loginUser) throws AuthenticationException {
        log.info("Received a request to generate a token for user {}", loginUser.getUsername());
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUser.getUsername(),
                        loginUser.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenUtil.generateToken(authentication);
        return ResponseEntity.ok(new AuthToken(token));
    }

    @RequestMapping(value="/register", method = RequestMethod.POST)
    @CrossOrigin(origins = "http://localhost:8080")
    public Users saveUser(@RequestBody UserDto user){
        log.info("Saving user {} to the database", user);
        return userService.save(user);
    }


    //Apenas adms podem ler a partir desta rota
    @PreAuthorize("hasRole('ROLE_ROLE_ADMIN')")
    @CrossOrigin(origins = "http://localhost:8080")
    @RequestMapping(value="/adminping", method = RequestMethod.GET)
    public String adminPing(){
        log.info("Received a admin request");
        return null;
    }

    //Apenas usuarios podem ler a partir dessa rota
    @PreAuthorize("hasRole('ROLE_ROLE_USER')")
    @CrossOrigin(origins = "http://localhost:8080")
    @RequestMapping(value="/userping", method = RequestMethod.GET)
    public String userPing(){
        log.info("Received a user request");
        return null;
    }

}
