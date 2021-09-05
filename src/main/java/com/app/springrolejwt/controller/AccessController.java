package com.app.springrolejwt.controller;

import com.app.springrolejwt.repository.implementation.RefreshTokenServiceImpl;
import com.app.springrolejwt.repository.implementation.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/get")
public class AccessController {

	@Autowired
	private RefreshTokenServiceImpl refreshTokenService;

	@Autowired
	UserDetailsServiceImpl userDetailsService;

	@GetMapping("/all")
	public String allAccess() {
		return "THIS IS PUBLIIIIIIIIC.";
	}
	
	@GetMapping("/user")
	@PreAuthorize("hasRole('USER') or hasRole('RESPONSIBLE')")
	public String userAccess() {
		return "Dashboard for elderly or anyone :).";
	}

	@GetMapping("/responsible")
	@PreAuthorize("hasRole('RESPONSIBLE')")
	public String responsibleAccess() {
		return "Responsible has access to this dashboard! ";
	}
}
