package com.app.springrolejwt.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/get")
public class AccessController {
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
		return "Responsible has access dashboard! ";
	}
}
